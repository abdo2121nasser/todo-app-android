package com.example.todo_app.features.profile_feature.data.repository

import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.profile_feature.data.entities.ProfileEntity
import com.example.todo_app.utils.constants.profileRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileRepository() {

    suspend fun getProfileData(token:String): Response<ProfileEntity> {

        return withContext(Dispatchers.IO) {
            val response= profileRetrofit.request.getProfileData(token)
            if (response.isSuccessful) {
                Log.d("response", "get profile data Success: ${response.body()}")
                return@withContext response
            } else if (response.code() == 401
            ) {
                Log.d("response", "Unauthorized (401), trying to refresh token...")
                return@withContext response
            } else {
                Log.d("response", "get profile data Error:  ${response.message()}")
                return@withContext response
            }
        }
    }


}