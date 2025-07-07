package com.example.todo_app.authentication_feature.presentation_layer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityRegisterBinding
import com.example.todo_app.databinding.PasswordInputLayoutBinding
import com.example.todo_app.databinding.PhoneInputLayoutBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.lang.Error

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var name: TextInputEditText
    private lateinit var numberOfExperience: TextInputEditText
    private lateinit var phoneNumber: PhoneInputLayoutBinding
    private lateinit var experienceLevel: AutoCompleteTextView
    private lateinit var address: TextInputEditText
    private lateinit var password: PasswordInputLayoutBinding
    private lateinit var signUpButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        initVariables()
    }

    private fun initVariables() {
        name = registerBinding.name
        phoneNumber = registerBinding.phoneLayout
        numberOfExperience = registerBinding.noExperienceYears
        experienceLevel = registerBinding.experienceLevel.dropdownMenu
        address = registerBinding.address
        password = registerBinding.passwordLayout
        signUpButton = registerBinding.signUpButton
        setupCountryDropdown()

    }

    private fun setupCountryDropdown() {
        val countries = listOf("fresh", "junior", "midLevel", "senior")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        experienceLevel
            .setAdapter(adapter)
    }


    @SuppressLint("ResourceAsColor")
    private fun validateData(): Boolean {
        var isValid = true

        if (name.text.isNullOrBlank()) {
            registerBinding.nameLayout.error = "Name is required"
            isValid = false
        } else {
            registerBinding.nameLayout.error = null
        }

        if (phoneNumber.phoneEditText.text.isNullOrBlank()) {
            phoneNumber.phoneLayout.error = "Phone number is required"
            phoneNumber.phoneContainer.strokeColor= getColor(R.color.red)
            isValid = false
        } else {
            phoneNumber.phoneLayout.error = null
            phoneNumber.phoneContainer.strokeColor= getColor(R.color.grey)

        }

        if (numberOfExperience.text.isNullOrBlank()) {
            registerBinding.noExperienceYearsLayout.error = "Enter number of years"
            isValid = false
        } else {
            registerBinding.noExperienceYearsLayout.error = null
        }

        if (experienceLevel.text.isNullOrBlank()) {
            registerBinding.experienceLevel.experienceLevelLayout.error = "Select experience level"
            isValid = false
        } else {
            registerBinding.experienceLevel.experienceLevelLayout.error = null
        }

        if (address.text.isNullOrBlank()) {
            registerBinding.addressLayout.error = "Address is required"
            isValid = false
        } else {
            registerBinding.addressLayout.error = null
        }

        if (password.password.text.isNullOrBlank()) {
            password.passwordLayout.error = "Password is required"
            isValid = false
        } else if (password.password.text!!.length < 6) {
            password.passwordLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            password.passwordLayout.error = null
        }
        return isValid

    }

    fun signUp(view: View) {
            try {
                if (validateData()) {
                    Log.d("test", "valid data")
                }
            } catch (e: Error) {
                Log.d("test", e.message.toString())

            }
        }



}
