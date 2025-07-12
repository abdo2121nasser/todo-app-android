package com.example.todo_app.features.authentication_feature.data_layer.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo_app.utils.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.RoomDB.AUTH_ENTITY)
data class AuthResponseModel(
    @PrimaryKey
    @ColumnInfo("_id")
    @SerializedName("_id")
    val id: String,

    @ColumnInfo("access_token")
    @SerializedName("access_token")
    val accessToken: String,

    @ColumnInfo("refresh_token")
    @SerializedName("refresh_token")
    val refreshToken: String
)
