package com.example.todo_app.task_feature.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.utils.Constants

typealias ui = Constants.UiStrings

class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private   val categories:List<String> = listOf(ui.ALL,ui.IN_PROGRESS,ui.WAITING,ui.FINISH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.categoryRecycleView.adapter = CategoryAdapter(this,categories)
    }



}