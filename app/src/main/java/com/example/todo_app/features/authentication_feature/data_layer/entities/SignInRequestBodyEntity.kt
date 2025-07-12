package com.example.todo_app.features.authentication_feature.data_layer.entities

import com.google.gson.annotations.SerializedName

data class SignInRequestBodyEntity(
    val phone: String,
    val password: String
)
