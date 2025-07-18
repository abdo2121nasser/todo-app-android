package com.example.todo_app.features.authentication_feature.presentation_layer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.R
import com.example.todo_app.features.authentication_feature.data_layer.AuthenticationRepo
import com.example.todo_app.features.authentication_feature.data_layer.entities.SignUpRequestBodyEntity
import com.example.todo_app.databinding.ActivityRegisterBinding
import com.example.todo_app.databinding.PasswordInputLayoutBinding
import com.example.todo_app.databinding.PhoneInputLayoutBinding
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.presentation.HomeTaskActivity
import com.example.todo_app.utils.constants.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var name: TextInputEditText
    private lateinit var numberOfExperience: TextInputEditText
    private lateinit var phoneNumber: PhoneInputLayoutBinding
    private lateinit var experienceLevel: AutoCompleteTextView
    private lateinit var address: TextInputEditText
    private lateinit var passwordBinding: PasswordInputLayoutBinding
    private lateinit var signUpButton: MaterialButton
    private var authRepo: AuthenticationRepo = AuthenticationRepo(this)
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
        passwordBinding = registerBinding.passwordLayout
        signUpButton = registerBinding.signUpButton
        setupCountryDropdown()

    }

    fun goToSignInScreen(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun setupCountryDropdown() {
        val countries = listOf("fresh", "junior", "midLevel", "senior")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        experienceLevel
            .setAdapter(adapter)
    }


    private val isValidateData: Boolean
        get() {
            var isValid = true

            if (name.text.isNullOrBlank()) {
                registerBinding.nameLayout.error = "Name is required"
                isValid = false
            } else {
                registerBinding.nameLayout.error = null
            }

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

            if (numberOfExperience.text.isNullOrBlank()) {
                registerBinding.noExperienceYearsLayout.error = "Enter number of years"
                isValid = false
            } else {
                registerBinding.noExperienceYearsLayout.error = null
            }

            if (experienceLevel.text.isNullOrBlank()) {
                registerBinding.experienceLevel.experienceLevelLayout.error =
                    "Select experience level"
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

            if (passwordBinding.password.text.isNullOrBlank()) {
                passwordBinding.passwordLayout.error = "Password is required"

                isValid = false
            } else if (passwordBinding.password.text!!.length < 6) {
                passwordBinding.passwordLayout.error = "Password must be at least 6 characters"

                isValid = false
            } else {
                passwordBinding.passwordLayout.error = null
            }
            return isValid
        }

    private val signUpRequestBodyEntity: SignUpRequestBodyEntity
        get() = SignUpRequestBodyEntity(
            name = name.text.toString(),
            '+' + phoneNumber.countryCodeHolder.selectedCountryCode.toString() + phoneNumber.phoneEditText.text.toString(),
            experience = numberOfExperience.text.toString().toInt(),
            level = experienceLevel.text.toString(),
            address = address.text.toString(),
            password = passwordBinding.password.text.toString()
        )


    fun signUp(view: View) {
        if (isValidateData) {
            lifecycleScope.launch {
                view.visibility = View.GONE
                registerBinding.circularProgressBar.visibility = View.VISIBLE
                val auth: AuthResponseModel? = authRepo.signUpRequest(signUpRequestBodyEntity)
                view.visibility = View.VISIBLE
                registerBinding.circularProgressBar.visibility = View.GONE
                if (auth != null) {
                    Toast.makeText(this@RegisterActivity, "Success", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this@RegisterActivity, HomeTaskActivity::class.java)
                    )
                    finishAffinity()
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Failed", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }


}


