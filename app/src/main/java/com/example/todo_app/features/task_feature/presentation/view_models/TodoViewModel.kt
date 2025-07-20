package com.example.todo_app.features.task_feature.presentation.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.todo_app.features.authentication_feature.data_layer.AuthenticationRepo
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.utils.constants.headers
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class TodoViewModel(
    private val app: Application,
    private val todoRepo: TodoRepository,
    private val authRepo: AuthenticationRepo
) : AndroidViewModel(app) {
     var todoItems: LiveData<List<TodoItemEntity>> = RoomDBHelper.getInstance(app).todoDao.getItems()
//    .map {item->
//         item.sortedBy {
//             it.title
//         }
//     }

    var authModel: LiveData<AuthResponseModel> = RoomDBHelper.getInstance(app).authDao.get()

    private var hasFetchedFromApi = false

    fun tryFetchFromApiOnce(pageNumber: Int = 1) {
        if (hasFetchedFromApi || authModel.value == null) return
        hasFetchedFromApi = true
        fetchTodoItems(pageNumber)
    }
    
    private fun readAllItems() = RoomDBHelper.getInstance(app).todoDao.getItems()
    private fun fetchTodoItems(
        pageNumber: Int,
        didRetry: Boolean = true
    ) {
        if (authModel.value == null) return;
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val model: AuthResponseModel =
                        authModel.value!!.copy(accessToken = headers.BEAR_TOKEN + authModel.value!!.accessToken)
                    val response: Response<List<TodoItemEntity>> =
                        todoRepo.getTodoPage(pageNumber, model.accessToken);
                    if (response.isSuccessful) {
                        val items: List<TodoItemEntity> = response.body()!!
                        todoRepo.upsertItems(items)
                    } else
                        if (response.code() == 401 && didRetry) {
                            getNewAccessToken(model.refreshToken)?.let {
                                authRepo.updateStoredAuth(model.copy(accessToken = it))
                                fetchTodoItems(pageNumber, false)
                            }
                        } else {
                            todoItems = readAllItems()
                        }
                } catch (e: Exception) {
                    Log.d("response", "get todo page Exception: ${e.localizedMessage}")
                }
            }
        }
    }

    suspend fun deleteTodoItem(itemId: String, ) {
        withContext(Dispatchers.IO) {
            val accessToken = headers.BEAR_TOKEN + authModel.value?.accessToken
            try {
                val response = todoRepo.deleteTodoItem(itemId, accessToken)
                if (response.isSuccessful) {
                    RoomDBHelper.getInstance(app).todoDao.delete(response.body()!!)
                } else {
                    Log.e("response", "something wrong happen")
                }
            } catch (e: Exception) {
                Log.e("response", "create todo item Exception: ${e.localizedMessage}")

            }

        }

    }

    private suspend fun getNewAccessToken(refreshToken: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = authRepo.refreshToken(refreshToken)
                if (response.isSuccessful) {
                    return@withContext response.body()?.string()
                        ?.let { JSONObject(it).getString("access_token") }
                }
            } catch (e: Exception) {
                Log.d("response", "refresh Exception: ${e.localizedMessage}")
            }
            null
        }
    }


    companion object {
        fun provideFactory(
            app: Application,
            todoRepo: TodoRepository,
            authRepo: AuthenticationRepo
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                        return TodoViewModel(app, todoRepo, authRepo) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
