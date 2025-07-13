package com.example.todo_app.features.splash_feature.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_app.R
import com.example.todo_app.features.authentication_feature.presentation_layer.LoginActivity
import com.example.todo_app.features.task_feature.presentation.HomeTaskActivity
import com.example.todo_app.utils.helpers.RoomDBHelper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val authModel = RoomDBHelper.getInstance(this)
            .authDao
            .get()
        authModel.observe(this) { model ->
            if (model == null)
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            else {
                startActivity(Intent(this@SplashActivity, HomeTaskActivity::class.java))
            }
        }


    }
}