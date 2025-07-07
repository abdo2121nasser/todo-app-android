package com.example.todo_app.authentication_feature.data_layer

import com.example.todo_app.utils.ApiConstants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST(ApiConstants.Endpoints.SIGN_UP)
    fun signUp(
      @Body signUpRequest:SignUpRequest
    ):Call<SignUpModel>
}