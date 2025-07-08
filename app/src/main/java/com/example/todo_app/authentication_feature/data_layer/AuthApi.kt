package com.example.todo_app.authentication_feature.data_layer

import com.example.todo_app.utils.ApiConstants
import retrofit2.*
import retrofit2.http.*

interface AuthApi {
    @POST(ApiConstants.Endpoints.SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequestBody
    ): Response<SignUpModel>
}