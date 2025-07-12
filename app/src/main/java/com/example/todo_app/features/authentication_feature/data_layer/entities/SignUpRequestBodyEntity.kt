package com.example.todo_app.features.authentication_feature.data_layer.entities

import com.google.gson.annotations.SerializedName

data class SignUpRequestBodyEntity(
    @SerializedName("displayName") val name: String,

    val phone: String, val password: String, val address: String, val level: String,

    @SerializedName("experienceYears") val experience: Int
)

