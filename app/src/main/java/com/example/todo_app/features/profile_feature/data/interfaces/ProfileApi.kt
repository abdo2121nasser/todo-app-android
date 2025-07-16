package com.example.todo_app.features.profile_feature.data.interfaces

import com.example.todo_app.features.profile_feature.data.entities.ProfileEntity
import com.example.todo_app.utils.constants.endPoint
import com.example.todo_app.utils.constants.headers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {
      @GET(endPoint.PROFILE)
    suspend fun getProfileData(
          @Header(headers.AUTH) token: String
      ):Response<ProfileEntity>


}