package com.example.todo_app.authentication_feature.data_layer.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.example.todo_app.authentication_feature.data_layer.entities.AuthResponseModel


@Dao
interface AuthDao {

    @Upsert
    fun upsert(auth:AuthResponseModel)
    @Delete
    fun delete(auth:AuthResponseModel)



}