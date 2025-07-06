package com.example.todo_app.authentication_feature.presentation_layer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.todo_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)


    }

    fun goToSignUpScreen(view: View) {}
}