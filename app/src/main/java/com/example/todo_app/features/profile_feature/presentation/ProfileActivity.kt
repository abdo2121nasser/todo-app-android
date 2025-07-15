package com.example.todo_app.features.profile_feature.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityProfileBinding
import com.example.todo_app.databinding.ProfileItemBinding
import com.example.todo_app.features.profile_feature.data.entities.ProfileItemEntity

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileBinding:ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)
        val list:List<ProfileItemEntity> = listOf(
            ProfileItemEntity("ds","sdsd",),
            ProfileItemEntity("ds","sdsd",),
            ProfileItemEntity("ds","sdsd",),
        )
        profileBinding.profileRecycleView.adapter=ProfileAdaptor(list)
    }
}