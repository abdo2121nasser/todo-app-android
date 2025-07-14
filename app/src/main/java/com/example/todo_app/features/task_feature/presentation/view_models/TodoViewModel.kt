package com.example.todo_app.features.task_feature.presentation.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository

class TodoViewModel(
    val todoRepo: TodoRepository
) : ViewModel() {

    companion object {
        fun provideFactory(
            todoRepo: TodoRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                        return TodoViewModel(todoRepo) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
