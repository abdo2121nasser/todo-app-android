package com.example.todo_app.utils.helpers

import com.example.todo_app.features.authentication_feature.data_layer.interfaces.AuthApi
import com.example.todo_app.features.profile_feature.data.interfaces.ProfileApi
import com.example.todo_app.features.task_feature.data.interfaces.TodoApi
import com.example.todo_app.utils.constants.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val retrofit = Retrofit.Builder().baseUrl(api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    object AuthRetrofit {
        val request: AuthApi = retrofit.create(AuthApi::class.java)
    }

    object TodoRetrofit {
        val request: TodoApi = retrofit.create(TodoApi::class.java)
    }
    object ProfileRetrofit {
        val request: ProfileApi = retrofit.create(ProfileApi::class.java)
    }


}