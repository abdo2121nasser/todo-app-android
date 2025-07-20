package com.example.todo_app.features.task_feature.data.entities

import com.google.gson.annotations.SerializedName

data class UpdateTodoItemRequestModel(
    @SerializedName("user")
    val userId: String,
    @SerializedName("image")
    val imageLink: String,
    val title: String,
    @SerializedName("desc")
    val subTitle: String,
    val status: String,
    val priority: String,
)
