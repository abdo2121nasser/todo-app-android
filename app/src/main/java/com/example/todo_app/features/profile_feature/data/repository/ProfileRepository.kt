package com.example.todo_app.features.profile_feature.data.repository

import android.content.Context
import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.profile_feature.data.entities.ProfileEntity
import com.example.todo_app.utils.constants.profileRetrofit
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileRepository(val context: Context) {

    suspend fun getProfileData(token: String): Response<ProfileEntity> {

        return withContext(Dispatchers.IO) {
            val response = profileRetrofit.request.getProfileData(token)
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

    suspend fun storeProfileData(profileEntity: ProfileEntity) {
        withContext(Dispatchers.IO) {
            try {
                RoomDBHelper.getInstance(this@ProfileRepository.context).profileDao.upsert(
                    profileEntity
                )
                Log.d("storage", "update profile data Success")

            } catch (e: Exception) {
                Log.d("storage", "updating profile data Exception: ${e.localizedMessage}")

            }
        }
    }
    suspend fun readProfileData(): ProfileEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val data = RoomDBHelper.getInstance(this@ProfileRepository.context).profileDao.get()
                Log.d("storage", "read profile data Success")
                return@withContext data
            } catch (e: Exception) {
                Log.d("storage", "read profile data Exception: ${e.localizedMessage}")
                return@withContext null
            }
        }
    }



}