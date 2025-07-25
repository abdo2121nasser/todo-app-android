package com.example.todo_app.features.task_feature.data.entities

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo_app.utils.constants.room
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = room.TODO_ENTITY)
data class TodoItemEntity(
    @PrimaryKey
    @ColumnInfo("_id")
    @SerializedName("_id")
    val id: String,

    @SerializedName("image")
    val imageLink:String,
    val title:String,
    @SerializedName("desc")
    val subTitle:String,
    val status:String,
    val priority:String,
    @SerializedName("createdAt")
    val date:String)
    : Parcelable
