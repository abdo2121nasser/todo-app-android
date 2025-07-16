package com.example.todo_app.features.profile_feature.data.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.profile_feature.data.entities.ProfileEntity
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity

@Dao
interface ProfileDao {


    @Upsert
    suspend fun upsert(profileEntity: ProfileEntity)

    @Query("SELECT * FROM `ProfileEntity` LIMIT 1")
    suspend fun get(): ProfileEntity?

}