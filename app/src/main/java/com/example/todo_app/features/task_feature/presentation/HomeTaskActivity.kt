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
    private val todoRepo: TodoRepository = TodoRepository(this)
    private val categoryAdapter: CategoryAdapter = CategoryAdapter(this, ui.categories)
    private val todoItems = MutableLiveData<List<TodoItemEntity>>(emptyList())
    private val authModel: LiveData<AuthResponseModel> =
        RoomDBHelper.getInstance(this).authDao.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        initVariables()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        authModel.observe(this) { model ->
            if (model != null && todoItems.value.isNullOrEmpty()) {
                fetchTodoItems()
            }
        }
    }

    private fun initVariables() {
        progressBar = homeBinding.progressBar
        homeBinding.categoryRecycleView.adapter = categoryAdapter
        buildTodoRecycleView()
    }

    private fun initObservers() {
        categoryAdapter.selectedIndex.observe(this) { index ->
            val filteredList: List<TodoItemEntity> = when (index) {
                1 -> todoItems.value?.filter { it.status == ui.IN_PROGRESS.lowercase() }
                    ?: emptyList()

                2 -> todoItems.value?.filter { it.status == ui.WAITING.lowercase() } ?: emptyList()
                3 -> todoItems.value?.filter { it.status == ui.FINISH.lowercase() } ?: emptyList()
                else -> todoItems.value ?: emptyList()
            }
            buildTodoRecycleView(filteredItems = MutableLiveData(filteredList))
        }
    }

    private fun buildTodoRecycleView(
        filteredItems: MutableLiveData<List<TodoItemEntity>> =
            MutableLiveData<List<TodoItemEntity>>(emptyList())
    ) {
        todoItems.observe(this) { items ->
            homeBinding.todoRecycleView.adapter =
                TodoAdapter(
                    this@HomeTaskActivity,
                    if (filteredItems.value.isNullOrEmpty())
                        items else filteredItems.value ?: emptyList()
                )
            progressBar.visibility = View.GONE
            homeBinding.todoRecycleView.visibility = View.VISIBLE
        }
    }

    private fun fetchTodoItems() {
        progressBar.visibility = View.VISIBLE
        homeBinding.todoRecycleView.visibility = View.GONE
        val model: AuthResponseModel = authModel.value!!
        lifecycleScope.launch {
            val items = todoRepo.getTodoPage(1, model)
            Log.d("recycle_view", "init $items")
            todoItems.postValue(items)
        }
    }


}
