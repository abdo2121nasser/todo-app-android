package com.example.todo_app.features.task_feature.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.features.authentication_feature.data_layer.entities.AuthResponseModel
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.features.task_feature.presentation.adaptors.CategoryAdapter
import com.example.todo_app.features.task_feature.presentation.adaptors.TodoAdapter
import com.example.todo_app.features.task_feature.presentation.view_models.TodoViewModel
import com.example.todo_app.utils.helpers.RoomDBHelper
import com.example.todo_app.utils.constants.ui
import kotlinx.coroutines.launch


class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private lateinit var progressBar: ProgressBar
    private val todoRepo: TodoRepository = TodoRepository(this)
    private val categoryAdapter: CategoryAdapter = CategoryAdapter(this, ui.categories)
    private val todoItems = MutableLiveData<List<TodoItemEntity>>(emptyList())
    private val authModel: LiveData<AuthResponseModel> =
        RoomDBHelper.getInstance(this).authDao.get()
    private lateinit var todoViewModel: TodoViewModel

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
        todoViewModel = ViewModelProvider(
            this,
            TodoViewModel.provideFactory(TodoRepository(this))
        )[TodoViewModel::class.java]
        progressBar = homeBinding.progressBar
        homeBinding.categoryRecycleView.adapter = categoryAdapter
//        buildTodoRecycleView()
    }

    private fun initObservers() {
        categoryAdapter.selectedIndex.observe(this) { index ->
            val filtered = getFilteredItems(index)
            buildTodoRecycleView(filtered)
        }
        todoItems.observe(this) {
            buildTodoRecycleView(it)
        }
    }

    private fun getFilteredItems(index: Int?): List<TodoItemEntity> {
        val allItems = todoItems.value ?: emptyList()
        val filtered = when (index) {
            1 -> allItems.filter { it.status.equals(ui.IN_PROGRESS, ignoreCase = true) }
            2 -> allItems.filter { it.status.equals(ui.WAITING, ignoreCase = true) }
            3 -> allItems.filter { it.status.equals(ui.FINISH, ignoreCase = true) }
            else -> allItems
        }
        return filtered
    }

    private fun buildTodoRecycleView(
        items: List<TodoItemEntity>
    ) {
            homeBinding.todoRecycleView.adapter = TodoAdapter(this, items)
            progressBar.visibility = View.GONE
            homeBinding.todoRecycleView.visibility = View.VISIBLE

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
