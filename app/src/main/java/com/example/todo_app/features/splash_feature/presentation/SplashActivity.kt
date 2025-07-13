package com.example.todo_app.features.splash_feature.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.R
import com.example.todo_app.features.authentication_feature.presentation_layer.LoginActivity
import com.example.todo_app.features.splash_feature.data.NavigationRepository
import com.example.todo_app.features.task_feature.presentation.HomeTaskActivity
import com.example.todo_app.utils.Constants
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val navigationRepo: NavigationRepository = NavigationRepository(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val authModel = navigationRepo.getAuthModel()
            val intent = if (authModel == null)
                Intent(this@SplashActivity, LoginActivity::class.java)
            else
                Intent(this@SplashActivity, HomeTaskActivity::class.java)
                    .putExtra(Constants.NavigationExtras.AUTH, authModel)
            startActivity(intent)
        }
    }
}