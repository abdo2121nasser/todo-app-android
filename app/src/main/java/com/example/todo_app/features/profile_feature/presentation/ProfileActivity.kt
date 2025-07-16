package com.example.todo_app.features.profile_feature.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityProfileBinding
import com.example.todo_app.databinding.ProfileItemBinding
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.profile_feature.data.entities.ProfileItemEntity
import com.example.todo_app.features.profile_feature.data.repository.ProfileRepository
import com.example.todo_app.features.profile_feature.presentation.controllers.ProfileViewModel
import com.example.todo_app.utils.constants.Constants
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
        initVariables()
        getProfileData()
    }

    private fun initVariables() {
        profileViewModel = ViewModelProvider(
            this,
            ProfileViewModel.provideFactory(application, ProfileRepository(this))
        )[ProfileViewModel::class.java]
        profileViewModel.authModel =
            intent.getParcelableExtra<AuthResponseModel>(Constants.NavigationExtras.AUTH)


    }


    private fun getProfileData() {
        lifecycleScope.launch {
            profileBinding.progressBar.visibility = View.VISIBLE
            profileBinding.profileRecycleView.visibility = View.GONE
            profileViewModel.tryReadProfileData()

            profileViewModel.profileEntity.observe(this@ProfileActivity){
            if(it != null){
                profileBinding.progressBar.visibility = View.GONE
                profileBinding.profileRecycleView.visibility = View.VISIBLE
                profileBinding.profileRecycleView.adapter =
                    ProfileAdaptor(profileViewModel.profileItems)
            }

            }
        }
    }

    fun returnToHomeScreen(view: View) {
        finish()
    }
}