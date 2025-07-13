package com.example.todo_app.features.task_feature.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.utils.Constants
import kotlinx.coroutines.launch

typealias ui = Constants.UiStrings


class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private lateinit var progressBar: ProgressBar
    private val todoRepo: TodoRepository = TodoRepository()
    private val categories: List<String> = listOf(ui.ALL, ui.IN_PROGRESS, ui.WAITING, ui.FINISH)
    private lateinit var todoItems: List<TodoItemEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        initVariables()

    }

    private fun fetchTodoItems() {
        progressBar.visibility = View.VISIBLE
        homeBinding.todoRecycleView.visibility = View.GONE
        lifecycleScope.launch {
            val token = Constants.Api.Headers.BEAR_TOKEN +
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjQ5ZmIyZWVmMGJmOTNkZDAwNzExYmEiLCJpYXQiOjE3NTIzMzYzNTEsImV4cCI6MTc1MjMzNjk1MX0.LHClrEdTVNDbToco-Roa1Sp6ta9u9LeI42dTr7FKsbw"
            todoItems = todoRepo.getTodoPage(1, token)
            homeBinding.todoRecycleView.adapter = TodoAdapter(this@HomeTaskActivity, todoItems)
            progressBar.visibility = View.GONE
            homeBinding.todoRecycleView.visibility = View.VISIBLE
        }
    }


    private fun initVariables() {
        progressBar = homeBinding.progressBar
        homeBinding.categoryRecycleView.adapter = CategoryAdapter(this, categories)
        fetchTodoItems()
    }

}
