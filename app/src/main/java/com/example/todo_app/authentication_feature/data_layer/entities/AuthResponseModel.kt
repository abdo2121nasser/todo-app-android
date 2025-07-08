package com.example.todo_app.authentication_feature.data_layer.entities

import com.google.gson.annotations.SerializedName

data class AuthResponseModel(
    @SerializedName("_id")
    val id: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
