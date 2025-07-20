package com.example.todo_app.features.task_feature.data.repositories

import android.content.Context
import android.util.Log
import com.example.todo_app.features.task_feature.data.entities.CreateTodoItemRequestEntity
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.entities.UpdateTodoItemRequestModel
import com.example.todo_app.utils.constants.todoRetrofit
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

   suspend fun createTodoItem(todoItemEntity: CreateTodoItemRequestEntity, token: String,): Response<TodoItemEntity>{
     return  withContext(Dispatchers.IO){
         val response = todoRetrofit.request.createTodoItem(todoItemEntity, token)
         if (response.isSuccessful) {
             Log.d("response", "create todo item Success: ${response.body()}")
             return@withContext response
         } else if (response.code() == 401
         ) {
             Log.d("response", "Unauthorized (401), trying to refresh token...")
             return@withContext response
         } else {
             Log.d("response", "create todo item Error:  ${response.message()}")
             return@withContext response
         }
     }
    }

    suspend fun updateTodoItem(item: UpdateTodoItemRequestModel, itemId:String, token:String):Response<TodoItemEntity>{
        return  withContext(Dispatchers.IO){
            val response = todoRetrofit.request.updateTodoItem(itemId = itemId, item = item,token= token)
            if (response.isSuccessful) {
                Log.d("response", "update todo item Success: ${response.body()}")
                return@withContext response
            } else if (response.code() == 401
            ) {
                Log.e("response", "Unauthorized (401), trying to refresh token...")
                return@withContext response
            } else {
                Log.e("response", "update todo item Error:  ${response.message()}")
                return@withContext response
            }
        }
    }
    suspend fun deleteTodoItem(itemId:String,token: String):Response<TodoItemEntity>{
        return  withContext(Dispatchers.IO){
            val response = todoRetrofit.request.deleteTodoItem(itemId = itemId,token)
            if (response.isSuccessful) {
                Log.d("response", "delete todo item Success: ${response.body()}")
                return@withContext response
            } else if (response.code() == 401
            ) {
                Log.e("response", "Unauthorized (401), trying to refresh token...")
                return@withContext response
            } else {
                Log.e("response", "delete todo item Error:  ${response.message()}")
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