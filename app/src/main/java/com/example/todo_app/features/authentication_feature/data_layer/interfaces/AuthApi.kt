package com.example.todo_app.features.authentication_feature.data_layer.interfaces

import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.Constants
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.http.*
import java.util.Objects

typealias endPoint = Constants.Api.Endpoints
typealias query = Constants.Api.Queries

interface AuthApi {
    @POST(Constants.Api.Endpoints.SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequestBodyEntity
    ): Response<AuthResponseModel>


    @POST(Constants.Api.Endpoints.SIGN_IN)
    suspend fun signIn(
        @Body signInRequestBodyEntity: SignInRequestBodyEntity
    ): Response<AuthResponseModel>

    @GET(Constants.Api.Endpoints.REFRESH)
    suspend fun refreshToken(
        @Query(query.TOKEN) refreshToken: String
    ): Response<ResponseBody>

}