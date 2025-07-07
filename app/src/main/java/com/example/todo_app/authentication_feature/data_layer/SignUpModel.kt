package com.example.todo_app.authentication_feature.data_layer

import com.google.gson.annotations.SerializedName
import java.util.concurrent.Callable

data class SignUpModel(
    @SerializedName("_id")
    val id: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
