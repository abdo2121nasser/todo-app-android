package com.example.todo_app.features.task_feature.data.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity

@Dao
interface TodoDao {

    @Upsert
   suspend fun upsert(items:List<TodoItemEntity>)

    @Query("select * from `todo item entity`")
    fun getItems():LiveData<List<TodoItemEntity>>



}