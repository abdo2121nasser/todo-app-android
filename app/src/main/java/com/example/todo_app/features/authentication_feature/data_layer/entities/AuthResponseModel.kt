package com.example.todo_app.features.authentication_feature.data_layer.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo_app.utils.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Constants.RoomDB.AUTH_ENTITY)
@Parcelize
data class AuthResponseModel(
    @PrimaryKey
    @ColumnInfo("_id")
    @SerializedName("_id")
    val id: String,

    @ColumnInfo("access_token")
    @SerializedName("access_token")
    var accessToken: String,

    @ColumnInfo("refresh_token")
    @SerializedName("refresh_token")
    val refreshToken: String
):Parcelable
