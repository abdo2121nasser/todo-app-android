package com.example.todo_app.features.task_feature.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.utils.constants.authRetrofit
import com.example.todo_app.utils.constants.todoRetrofit
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response


class TodoRepository(private val context: Context) {

    suspend fun getTodoPage(
        pageNumber: Int,
        token: String,
    ): Response<List<TodoItemEntity>> {
        return withContext(Dispatchers.IO) {
            val response = todoRetrofit.request.getTodoPage(pageNumber, token)
            if (response.isSuccessful) {
                Log.d("response", "get todo page Success: ${response.body()}")
                return@withContext response
            } else if (response.code() == 401
            ) {
                Log.d("response", "Unauthorized (401), trying to refresh token...")
                return@withContext response
            } else {
                Log.d("response", "get todo page Error:  ${response.message()}")
                return@withContext response
            }
        }
    }

    suspend fun upsertItems(items: List<TodoItemEntity>) {
        withContext(Dispatchers.IO) {
            try {
                RoomDBHelper.getInstance(context).todoDao.upsert(items)
                Log.d("storage", "update Success")

            } catch (e: Exception) {
                Log.d("storage", "updating todo items Exception: ${e.localizedMessage}")

            }
        }
    }


}