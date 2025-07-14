package com.example.todo_app.features.authentication_feature.data_layer

import android.content.Context
import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.*
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthenticationRepo(private val context: Context) {

    suspend fun signUpRequest(signUpRequestBody: SignUpRequestBodyEntity): AuthResponseModel? {

        val request = authRetrofit.request
        return withContext(Dispatchers.IO) {
            try {
                val response = request.signUp(signUpRequest = signUpRequestBody)
                if (response.isSuccessful) {
                    val result: AuthResponseModel? = response.body()
                    result?.let {
                        RoomDBHelper.getInstance(context).authDao.upsert(auth = it)
                    }
                    Log.d("response", "Success: $result")
                    return@withContext result
                } else {
                    Log.d("response", "Error: ${response.errorBody()?.string()}")
                }
                null
            } catch (e: Exception) {
                Log.e("response", "Exception: ${e.localizedMessage}")
                null
            }

        }

    }

    suspend fun signInRequest(signInRequestBodyEntity: SignInRequestBodyEntity): AuthResponseModel? {
        val request = authRetrofit.request
        return withContext(Dispatchers.IO) {
            try {
                val response = request.signIn(signInRequestBodyEntity = signInRequestBodyEntity)
                if (response.isSuccessful) {
                    val result: AuthResponseModel? = response.body()

                    result?.let {
                        RoomDBHelper.getInstance(context).authDao
                            .upsert(auth = it)
                    }
                    Log.d("response", "Success: $result")
                   return@withContext result
                } else {
                    Log.d("response", "Error: ${response.errorBody()?.string()}")
                }
                null
            } catch (e: Exception) {
                Log.e("response", "Exception: ${e.localizedMessage}")
                null
            }
        }

    }


}