package com.example.todo_app.features.task_feature.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.features.authentication_feature.data_layer.AuthenticationRepo
import com.example.todo_app.features.profile_feature.presentation.ProfileActivity
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.features.task_feature.presentation.adaptors.CategoryAdapter
import com.example.todo_app.features.task_feature.presentation.adaptors.TodoAdapter
import com.example.todo_app.features.task_feature.presentation.view_models.TodoViewModel
import com.example.todo_app.utils.constants.Constants
import com.example.todo_app.utils.constants.nav
import com.example.todo_app.utils.constants.ui
import kotlinx.coroutines.launch


class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private lateinit var progressBar: ProgressBar
    private val categoryAdapter: CategoryAdapter = CategoryAdapter(this, ui.categories)
    private lateinit var todoViewModel: TodoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        initVariables()
        initObservers()
    }

    private fun initVariables() {
        todoViewModel = ViewModelProvider(
            this,
            TodoViewModel.provideFactory(
                application,
                TodoRepository(this),
                AuthenticationRepo(this)
            )
        )[TodoViewModel::class.java]
//        todoViewModel.fetchAuthModel()
        progressBar = homeBinding.progressBar
        homeBinding.categoryRecycleView.adapter = categoryAdapter
    }

    private fun initObservers() {

        categoryAdapter.selectedIndex.observe(this) { index ->
            val filtered = getFilteredItems(index)
            buildTodoRecycleView(filtered)
        }
        todoViewModel.authModel.observe(this) { model ->
            if (model != null) {
                todoViewModel.tryFetchFromApiOnce()
            }
        }
        todoViewModel.todoItems.observe(this) {
            buildTodoRecycleView(it)
        }


    }

    private fun getFilteredItems(index: Int?): List<TodoItemEntity> {
        val allItems = todoViewModel.todoItems.value ?: emptyList()
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

//    private fun fetchTodoItems() {
//        progressBar.visibility = View.VISIBLE
//        homeBinding.todoRecycleView.visibility = View.GONE
//        lifecycleScope.launch {
//            todoViewModel.fetchTodoItems(1)
//
//        }
//
//    }
    fun goToProfileScreen(view: View) {
        startActivity(
            Intent(this, ProfileActivity::class.java)
                .putExtra(Constants.NavigationExtras.AUTH, todoViewModel.authModel.value)

        )
    }
    fun addNewItem(view: View) {
        startActivity(
            Intent(this, AddTaskActivity::class.java)
                .putExtra(nav.AUTH, todoViewModel.authModel.value)
        )
    }
}
