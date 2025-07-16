package com.example.todo_app.features.profile_feature.presentation.controllers

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.profile_feature.data.entities.ProfileEntity
import com.example.todo_app.features.profile_feature.data.entities.ProfileItemEntity
import com.example.todo_app.features.profile_feature.data.repository.ProfileRepository
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.presentation.view_models.TodoViewModel
import com.example.todo_app.utils.constants.headers
import com.example.todo_app.utils.constants.profileRetrofit
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application, val profRepo: ProfileRepository) : AndroidViewModel(app) {

    var authModel: AuthResponseModel? = null
    private lateinit var profileEntity: ProfileEntity
    val profileItems:List<ProfileItemEntity> get() = listOf(
        ProfileItemEntity("NAME", profileEntity.name),
        ProfileItemEntity("PHONE", profileEntity.phone),
        ProfileItemEntity("LEVEL", profileEntity.level),
        ProfileItemEntity("YEARS OF EXPERIENCE", profileEntity.experience.toString()),
        ProfileItemEntity("LOCATION", profileEntity.address),
    )

  suspend  fun getProfileData() {
        if (authModel == null) return;
        val token: String = headers.BEAR_TOKEN + authModel!!.accessToken
            try {
                val response = profRepo.getProfileData(token)
                if (response.isSuccessful) {
                    profileEntity = response.body()!!
//                    todoRepo.upsertItems(items)
                }
            } catch (e: Exception) {
                Log.d("response", "get profile data Exception: ${e.localizedMessage}")




        }
    }

    companion object {
        fun provideFactory(
            app: Application,
            profRepo: ProfileRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                        return ProfileViewModel(app, profRepo) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

}