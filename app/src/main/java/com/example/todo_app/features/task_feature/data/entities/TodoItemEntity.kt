package com.example.todo_app.features.task_feature.data.entities

import com.google.gson.annotations.SerializedName

data class TodoItemEntity(
    @SerializedName("image")
    val imageLink:String,
    val title:String,
    @SerializedName("desc")
    val subTitle:String,
    @SerializedName("createdAt")
    val date:String)
