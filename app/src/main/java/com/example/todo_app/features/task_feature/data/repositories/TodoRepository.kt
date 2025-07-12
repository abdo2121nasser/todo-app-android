package com.example.todo_app.features.task_feature.data.repositories

import android.util.Log
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.utils.Constants
import com.example.todo_app.utils.helpers.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

typealias retrofit = RetrofitHelper.TodoRetrofit

class TodoRepository {

    suspend fun getTodoPage(pageNumber: Int, token: String): List<TodoItemEntity> {

        return withContext(Dispatchers.IO) {
            try {
                val response = retrofit.request.getTodoPage(pageNumber, token)
                if (response.isSuccessful) {
                    Log.d("response", "Success: ${response.body()}")
                    return@withContext response.body() ?: emptyList()
                } else {
                    Log.d("response", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("response", "Exception: ${e.localizedMessage}")

            }
            emptyList()
        }
    }


}