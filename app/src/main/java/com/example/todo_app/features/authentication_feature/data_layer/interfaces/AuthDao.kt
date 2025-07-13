package com.example.todo_app.features.authentication_feature.data_layer.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel


@Dao
interface AuthDao {

    @Query("SELECT * FROM `authentication entity` LIMIT 1")
     fun get(): LiveData<AuthResponseModel>

    @Upsert
    suspend fun upsert(auth: AuthResponseModel)

    @Delete
    suspend fun delete(auth: AuthResponseModel)


}