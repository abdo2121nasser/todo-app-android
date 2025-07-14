package com.example.todo_app.features.task_feature.data.repositories

import android.content.Context
import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.presentation.HomeTaskActivity
import com.example.todo_app.utils.helpers.RoomDBHelper
import com.example.todo_app.utils.*
import com.example.todo_app.utils.constants.authRetrofit
import com.example.todo_app.utils.constants.headers
import com.example.todo_app.utils.constants.todoRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class TodoRepository(private val context: Context) {

    suspend fun getTodoPage(
        pageNumber: Int,
        authModel: AuthResponseModel,
        didRetry: Boolean = true
    ): List<TodoItemEntity> {
        return withContext(Dispatchers.IO) {
            try {
                val model = authModel.copy(accessToken = headers.BEAR_TOKEN + authModel.accessToken)
                val response = todoRetrofit.request.getTodoPage(pageNumber, model.accessToken)
                if (response.isSuccessful) {
                    Log.d("response", "get todo page Success: ${response.body()}")
                    return@withContext response.body() ?: emptyList<TodoItemEntity>();
                } else if (response.code() == 401 && didRetry) {
                    Log.d("response", "Unauthorized (401), trying to refresh token...")
                    return@withContext refreshHandler(model, pageNumber)
                } else {
                    Log.d("response", "get todo page Error:  ${response.message()}")
                }
                emptyList()
            } catch (e: Exception) {
                Log.d("response", "get todo page Exception: ${e.localizedMessage}")
                emptyList()
            }
        }
    }

    private suspend fun refreshHandler(
        authModel: AuthResponseModel,
        pageNumber: Int
    ): List<TodoItemEntity> {
        val token = refreshToken(authModel.refreshToken)
        if (token != null) {
            val model = authModel.copy(accessToken = token)
            updateAuth(model)
            return getTodoPage(pageNumber, model, false)
        }
        return emptyList()
    }


    private suspend fun refreshToken(refreshToken: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = authRetrofit.request.refreshToken(refreshToken)
                if (response.isSuccessful) {
                    val bodyString = response.body()?.string()
                    val accessToken = JSONObject(bodyString!!).getString("access_token")
                    Log.d("response", "refresh Success: $accessToken")
                    return@withContext accessToken
                } else {
                    Log.d("response", "refresh Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.d("response", "refresh Exception: ${e.localizedMessage}")
            }
            null
        }
    }

    private suspend fun updateAuth(model: AuthResponseModel) {
        withContext(Dispatchers.IO) {
            try {
                RoomDBHelper.getInstance(context).authDao.upsert(model)
                Log.d("response", "update Success")

            } catch (e: Exception) {
                Log.d("response", "updating auth Exception: ${e.localizedMessage}")

            }
        }
    }

}