package com.example.todo_app.authentication_feature.data_layer

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("displayName") val name: String,

    val phone: String, val password: String, val address: String, val level: String,

    @SerializedName("experienceYears") val experience: Int
)

