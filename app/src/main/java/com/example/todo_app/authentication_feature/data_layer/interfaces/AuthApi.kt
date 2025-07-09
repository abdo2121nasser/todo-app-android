package com.example.todo_app.authentication_feature.data_layer.interfaces

import com.example.todo_app.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.Constants
import retrofit2.*
import retrofit2.http.*

interface AuthApi {
    @POST(Constants.Api.Endpoints.SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequestBodyEntity
    ): Response<AuthResponseModel>


 @POST(Constants.Api.Endpoints.SIGN_IN)
    suspend fun signIn(
        @Body signInRequestBodyEntity: SignInRequestBodyEntity
    ): Response<AuthResponseModel>



}