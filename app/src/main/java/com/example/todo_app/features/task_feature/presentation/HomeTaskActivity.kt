package com.example.todo_app.features.task_feature.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.features.task_feature.presentation.adaptors.CategoryAdapter
import com.example.todo_app.features.task_feature.presentation.adaptors.TodoAdapter
import com.example.todo_app.utils.Constants
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.launch

typealias ui = Constants.UiStrings
typealias header = Constants.Api.Headers


class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private lateinit var progressBar: ProgressBar
    private val todoRepo: TodoRepository = TodoRepository()
    private val categories: List<String> = listOf(ui.ALL, ui.IN_PROGRESS, ui.WAITING, ui.FINISH)
    private val todoItems = MutableLiveData<List<TodoItemEntity>>(emptyList())
    private var authModel: LiveData<AuthResponseModel> =
        RoomDBHelper.getInstance(this).authDao.get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        initVariables()

    }

    override fun onStart() {
        super.onStart()
        authModel.observe(this) { model ->
            if (model != null) {
                fetchTodoItems()
            }
        }
    }

    private fun initVariables() {
        progressBar = homeBinding.progressBar
        homeBinding.categoryRecycleView.adapter = CategoryAdapter(this, categories)
        buildTodoRecycleView()

    }

    private fun buildTodoRecycleView() {

        todoItems.observe(this) { items ->
            homeBinding.todoRecycleView.adapter =
                TodoAdapter(this@HomeTaskActivity, items ?: emptyList())
            progressBar.visibility = View.GONE
            homeBinding.todoRecycleView.visibility = View.VISIBLE


        }
    }

    private fun fetchTodoItems() {
        progressBar.visibility = View.VISIBLE
        homeBinding.todoRecycleView.visibility = View.GONE
        val model: AuthResponseModel
        authModel.value.also {
            model = it!!
        }
        lifecycleScope.launch {
            val items = todoRepo.getTodoPage(1, model)
            Log.d("recycle_view", "init " + items.toString())

            todoItems.postValue(items)
        }
    }



}
