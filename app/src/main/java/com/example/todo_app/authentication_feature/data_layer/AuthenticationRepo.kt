package com.example.todo_app.authentication_feature.data_layer

import android.util.Log
import com.example.todo_app.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.ApiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthenticationRepo {

    suspend fun signUpRequest(signUpRequestBody: SignUpRequestBodyEntity) {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val request = retrofit.create(AuthApi::class.java)
        try {
            val response = request.signUp(signUpRequest = signUpRequestBody)
            if (response.isSuccessful) {
                val result = response.body()
                Log.d("response", "Success: $result")
            } else {
                Log.d("response", "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("response", "Exception: ${e.localizedMessage}")
        }
    }
    suspend fun signInRequest(signInRequestBodyEntity: SignInRequestBodyEntity) {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val request = retrofit.create(AuthApi::class.java)
        try {
            val response = request.signIn(signInRequestBodyEntity = signInRequestBodyEntity)
            if (response.isSuccessful) {
                val result = response.body()
                Log.d("response", "Success: $result")
            } else {
                Log.d("response", "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("response", "Exception: ${e.localizedMessage}")
        }
    }


}