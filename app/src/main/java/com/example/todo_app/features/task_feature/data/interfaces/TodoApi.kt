package com.example.todo_app.features.task_feature.data.interfaces

import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.utils.Constants
import com.example.todo_app.utils.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface TodoApi {

    @GET(endPoint.TODO)
    suspend fun getTodoPage(
        @Query(query.PAGE) pageNumber: Int,
        @Header(headers.AUTH) token: String
    ): Response<List<TodoItemEntity>>


}