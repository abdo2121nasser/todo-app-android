package com.example.todo_app.features.task_feature.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.features.authentication_feature.data_layer.AuthenticationRepo
import com.example.todo_app.features.authentication_feature.presentation_layer.LoginActivity
import com.example.todo_app.features.profile_feature.presentation.ProfileActivity
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.features.task_feature.presentation.adaptors.CategoryAdapter
import com.example.todo_app.features.task_feature.presentation.adaptors.TodoAdapter
import com.example.todo_app.features.task_feature.presentation.view_models.TodoViewModel
import com.example.todo_app.utils.constants.Constants
import com.example.todo_app.utils.constants.headers
import com.example.todo_app.utils.constants.nav
import com.example.todo_app.utils.constants.ui
import com.example.todo_app.utils.helpers.RoomDBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
                Log.d("auth",model.accessToken)
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
        homeBinding.todoRecycleView.adapter = TodoAdapter(this, items,::showMoreOptions,::onTodoItemClick)
        progressBar.visibility = View.GONE
        homeBinding.todoRecycleView.visibility = View.VISIBLE

    }


    fun goToProfileScreen(view: View) {
        startActivity(
            Intent(this, ProfileActivity::class.java)
                .putExtra(Constants.NavigationExtras.AUTH, todoViewModel.authModel.value)

        )
    }
    fun addNewItem(view: View) {
        startActivity(
            Intent(this, TaskActionActivity::class.java)
                .putExtra(nav.AUTH, todoViewModel.authModel.value)
        )
    }

    private fun showMoreOptions(it: View,todoItemEntity:TodoItemEntity) {
        val popupMenu = PopupMenu(this, it)
        popupMenu.menuInflater.inflate(R.menu.todo_options_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    startActivity(
                        Intent(this, TaskActionActivity::class.java)
                            .putExtra(nav.AUTH,todoViewModel.authModel.value)
                            .putExtra(nav.UPDATE_TODO_ENTITY,todoItemEntity)
                    )
                    true
                }
                R.id.delete -> {
                    lifecycleScope.launch {
                        todoViewModel.deleteTodoItem(todoItemEntity.id)
                    }
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }
    private fun onTodoItemClick(item:TodoItemEntity){
        startActivity(Intent(this,TaskDetailsActivity::class.java)
            .putExtra(nav.DETAILED_TODO_ENTITY,item)
        )
    }

    fun signOut(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            RoomDBHelper.getInstance(this@HomeTaskActivity).clearAllTables()
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@HomeTaskActivity, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }


}
