package com.example.todo_app.features.task_feature.data.entities

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CreateTodoItemRequestEntity(

    @SerializedName("image")
    val imageLink: String,
    val title: String,
    @SerializedName("desc")
    val subTitle: String,
    val priority: String,
    @SerializedName("dueDate")
    val date: String
)
