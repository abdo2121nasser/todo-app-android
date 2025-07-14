package com.example.todo_app.features.authentication_feature.data_layer

import android.content.Context
import android.util.Log
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.constants.authRetrofit
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response


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

    suspend fun refreshToken(refreshToken: String): Response<ResponseBody> {
        return withContext(Dispatchers.IO) {
            val response = authRetrofit.request.refreshToken(refreshToken)
            if (response.isSuccessful) {
                return@withContext  response
            } else {
                Log.d("response", "refresh Error: ${response.code()} - ${response.message()}")
                return@withContext response
            }
        }
    }


    suspend fun updateStoredAuth(model: AuthResponseModel) {
        withContext(Dispatchers.IO) {
            try {
                RoomDBHelper.getInstance(context).authDao.upsert(model)
                Log.d("response", "update Success")

            } catch (e: Exception) {
                Log.d("response", "updating auth Exception: ${e.localizedMessage}")

            }
        }
    }
}