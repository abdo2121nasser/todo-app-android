package com.example.todo_app.utils.helpers

import com.example.todo_app.authentication_feature.data_layer.interfaces.AuthApi
import com.example.todo_app.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {


    object AuthRetrofit {
        val request: AuthApi = Retrofit.Builder()
            .baseUrl(Constants.Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(AuthApi::class.java)
    }


}