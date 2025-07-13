package com.example.todo_app.features.task_feature.data.repositories

import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.presentation.header
import com.example.todo_app.utils.Constants
import com.example.todo_app.utils.helpers.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

typealias todoRetrofit = RetrofitHelper.TodoRetrofit
typealias authRetrofit = RetrofitHelper.AuthRetrofit
typealias header = Constants.Api.Headers

class TodoRepository {

    suspend fun getTodoPage(
        pageNumber: Int,
        authModel: AuthResponseModel,
        didRetry: Boolean = true
    ): List<TodoItemEntity> {
        return withContext(Dispatchers.IO) {
            try {
                val response = todoRetrofit.request.getTodoPage(pageNumber, authModel.accessToken)

                if (response.isSuccessful) {
                    Log.d("response", "get todo page Success: ${response.body()}")
                    return@withContext response.body() ?: emptyList<TodoItemEntity>();
                } else if (response.code() == 401 && didRetry) {
                    Log.d("response", "Unauthorized (401), trying to refresh token...")
                    authModel.apply {
                        val token = refreshToken(authModel.refreshToken)
                        if (token != null) {
                            accessToken = header.BEAR_TOKEN + token
                        } else {
                            return@withContext emptyList()
                        }
                    }
                    Log.d("response", "access token2: ${authModel.accessToken}")
                    return@withContext getTodoPage(pageNumber, authModel, false)
                } else {
                    Log.d(
                        "response",
                        "get todo page Error:  ${response.message()}"
                    )
                }
                emptyList()
            } catch (e: Exception) {
                Log.d("response", "get todo page Exception: ${e.localizedMessage}")
                emptyList()
            }
        }
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
                    Log.d("response", "refresh Error: ${refreshToken.toString()}")
                }
            } catch (e: Exception) {
                Log.d("response", "refresh Exception: ${e.localizedMessage}")
            }
            null
        }
    }


}