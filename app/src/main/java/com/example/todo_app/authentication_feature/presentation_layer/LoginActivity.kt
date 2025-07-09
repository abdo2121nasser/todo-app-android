package com.example.todo_app.authentication_feature.presentation_layer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.R
import com.example.todo_app.authentication_feature.data_layer.AuthenticationRepo
import com.example.todo_app.authentication_feature.data_layer.entities.SignInRequestBodyEntity
import com.example.todo_app.databinding.ActivityLoginBinding
import com.example.todo_app.databinding.PasswordInputLayoutBinding
import com.example.todo_app.databinding.PhoneInputLayoutBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var phoneNumber: PhoneInputLayoutBinding
    private lateinit var passwordBinding: PasswordInputLayoutBinding
    private lateinit var signInButton: MaterialButton
    private var authRepo: AuthenticationRepo = AuthenticationRepo(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        initVariables()


    }

    fun goToSignUpScreen(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finishAffinity()
    }

    private fun initVariables() {
        phoneNumber = loginBinding.phoneLayout
        passwordBinding = loginBinding.passwordLayout

    }

    private val isValidateData: Boolean
        get() {
            var isValid = true
            if (phoneNumber.phoneEditText.text.isNullOrBlank()) {
                phoneNumber.phoneLayout.error = "Phone number is required"
                phoneNumber.phoneContainer.strokeColor = getColor(R.color.red)
                isValid = false
            } else if (phoneNumber.phoneEditText.text!!.length !in 7..15) {
                phoneNumber.phoneLayout.error = "In Valid Phone Number"
                phoneNumber.phoneContainer.strokeColor = getColor(R.color.red)
                isValid = false
            } else {
                phoneNumber.phoneLayout.error = null
                phoneNumber.phoneContainer.strokeColor = getColor(R.color.grey)

            }

            if (passwordBinding.password.text.isNullOrBlank()) {
                passwordBinding.passwordLayout.error = "Password is required"

                isValid = false
            } else if (passwordBinding.password.text!!.length < 6) {
                passwordBinding.passwordLayout.error = "Password must be at least 6 characters"

                isValid = false
            } else {
                passwordBinding.passwordLayout.error = null
            }
            Log.d("phone", phoneNumber.phoneEditText.text.toString())

            return isValid
        }
    private val signInRequestBodyEntity: SignInRequestBodyEntity
        get() = SignInRequestBodyEntity(
            '+' + phoneNumber.countryCodeHolder.selectedCountryCode.toString() + phoneNumber.phoneEditText.text.toString(),
            password = passwordBinding.password.text.toString()
        )

    fun signIn(view: View) {
        if (isValidateData) {
            lifecycleScope.launch {
                view.visibility = View.GONE
                loginBinding.circularProgressBar.visibility = View.VISIBLE
                authRepo.signInRequest(signInRequestBodyEntity)
                view.visibility = View.VISIBLE
                loginBinding.circularProgressBar.visibility = View.GONE
            }
        }
    }

}