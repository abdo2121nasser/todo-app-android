package com.example.todo_app.authentication_feature.presentation_layer

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_app.databinding.ActivityRegisterBinding
import com.example.todo_app.databinding.ExperienceDropDownListLayoutBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        setupCountryDropdown()
    }

    private fun setupCountryDropdown() {
        val countries = listOf("fresh", "junior", "midLevel", "senior")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        registerBinding.experienceLevel.dropdownMenu
            .setAdapter(adapter)
    }

}
