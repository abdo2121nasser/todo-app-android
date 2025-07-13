package com.example.todo_app.features.splash_feature.data

import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.splash_feature.presentation.SplashActivity
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NavigationRepository(private val activity: SplashActivity) {

    suspend fun getAuthModel(): AuthResponseModel? {
        try {

            return withContext(Dispatchers.IO) {
                val dao = RoomDBHelper.getInstance(activity).authDao
                val auth = dao.get()
                Log.d("navigation", "User fetched from Room: $auth")
                auth

            }
        } catch (e: Exception) {
            Log.e("navigation", "Error fetching user from Room DB: ${e.localizedMessage}")
        }
        return null
    }


}