package com.example.todo_app.authentication_feature.data_layer

import android.content.Context
import android.util.Log
import com.example.todo_app.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.helpers.RetrofitHelper
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepo(private val context: Context) {

    suspend fun signUpRequest(signUpRequestBody: SignUpRequestBodyEntity) {

        val request = RetrofitHelper.AuthRetrofit.request
        withContext(Dispatchers.IO) {
            try {
                val response = request.signUp(signUpRequest = signUpRequestBody)
                if (response.isSuccessful) {
                    val result: AuthResponseModel? = response.body()
                    result?.let {
                        RoomDBHelper.getInstance(context).authDao.upsert(auth = it)
                    }
                    Log.d("response", "Success: $result")
                } else {
                    Log.d("response", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("response", "Exception: ${e.localizedMessage}")
            }

        }

    }

    suspend fun signInRequest(signInRequestBodyEntity: SignInRequestBodyEntity) {
        val request = RetrofitHelper.AuthRetrofit.request
        withContext(Dispatchers.IO) {
            try {
                val response = request.signIn(signInRequestBodyEntity = signInRequestBodyEntity)
                if (response.isSuccessful) {
                    val result: AuthResponseModel? = response.body()

                    result?.let {
                        RoomDBHelper.getInstance(context).authDao
                            .upsert(auth = it)
                    }
                    Log.d("response", "Success: $result")
                } else {
                    Log.d("response", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("response", "Exception: ${e.localizedMessage}")
            }
        }

    }


}