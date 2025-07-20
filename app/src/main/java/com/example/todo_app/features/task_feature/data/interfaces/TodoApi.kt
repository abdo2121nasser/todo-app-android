package com.example.todo_app.features.task_feature.data.interfaces

import com.example.todo_app.features.task_feature.data.entities.CreateTodoItemRequestEntity
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.entities.UpdateTodoItemRequestModel
import com.example.todo_app.utils.*
import com.example.todo_app.utils.constants.endPoint
import com.example.todo_app.utils.constants.headers
import com.example.todo_app.utils.constants.query
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface TodoApi {

    @GET(endPoint.TODO)
    suspend fun getTodoPage(
        @Query(query.PAGE) pageNumber: Int,
        @Header(headers.AUTH) token: String
    ): Response<List<TodoItemEntity>>

    @POST(endPoint.CREATE_TODO_ITEM)
    suspend fun createTodoItem(
        @Body  item: CreateTodoItemRequestEntity,
        @Header(headers.AUTH) token: String
    ): Response<TodoItemEntity>

    @PUT(endPoint.UPDATE_TODO_ITEM )
    suspend fun updateTodoItem(
        @Path("id") itemId : String,
        @Body  item: UpdateTodoItemRequestModel,
        @Header(headers.AUTH) token: String
    ): Response<TodoItemEntity>

    @PUT(endPoint.DELETE_TODO_ITEM )
    suspend fun deleteTodoItem(
        @Path("id") itemId : String,
        @Header(headers.AUTH) token: String
    ): Response<TodoItemEntity>



}