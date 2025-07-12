package com.example.todo_app.utils.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.authentication_feature.data_layer.interfaces.AuthDao
import com.example.todo_app.utils.Constants

@Database(entities = [AuthResponseModel::class], version = 1)
abstract class RoomDBHelper : RoomDatabase() {

    abstract val authDao: AuthDao

    companion object {

        @Volatile
        private var INSTANCE: RoomDBHelper? = null
        fun getInstance(context: Context): RoomDBHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    RoomDBHelper::class.java,
                    Constants.RoomDB.TODO_DB
                ).build()
                INSTANCE = instance
                instance
            }

        }


    }


}