package com.example.todo_app.features.profile_feature.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class ProfileEntity(
    @PrimaryKey
    @ColumnInfo("_id")
    @SerializedName("_id")
    val id: String,
    @SerializedName("displayName")
    val name: String,
    @SerializedName("username")
    val phone: String,
    val address: String,
    val level: String,
    @SerializedName("experienceYears")
    val experience: Int
)
