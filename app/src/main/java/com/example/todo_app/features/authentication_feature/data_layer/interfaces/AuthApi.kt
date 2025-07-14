package com.example.todo_app.features.authentication_feature.data_layer.interfaces

import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.utils.*
import com.example.todo_app.utils.constants.endPoint
import com.example.todo_app.utils.constants.query
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.http.*


interface AuthApi {
    @POST(endPoint.SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequestBodyEntity
    ): Response<AuthResponseModel>


    @POST(endPoint.SIGN_IN)
    suspend fun signIn(
        @Body signInRequestBodyEntity: SignInRequestBodyEntity
    ): Response<AuthResponseModel>

    @GET(endPoint.REFRESH)
    suspend fun refreshToken(
        @Query(query.TOKEN) refreshToken: String
    ): Response<ResponseBody>

}