package com.example.todo_app.features.task_feature.presentation.view_models

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.features.task_feature.data.entities.CreateTodoItemRequestEntity
import com.example.todo_app.features.task_feature.data.repositories.TodoRepository
import com.example.todo_app.utils.constants.headers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskViewModel(app: Application, private val todoRepo: TodoRepository) : AndroidViewModel(app) {

    var selectedDate: String? = null
    var selectedImage: String? = null
    var selectedPriority: String? = null


    suspend fun createTodoItem(todoItemEntity: CreateTodoItemRequestEntity, token: String){
        withContext(Dispatchers.IO){
            val accessToken=headers.BEAR_TOKEN+token
            try {
                val response=  todoRepo.createTodoItem(todoItemEntity,accessToken)
                if (response.isSuccessful) {
                    todoRepo.upsertItems(listOf( response.body()!!))
                }
                else{
                    Log.d("response", "somthing wrong happen")
                }
            }catch (e:Exception){
                Log.d("response", "create todo item Exception: ${e.localizedMessage}")

            }

        }

    }


    companion object {
        fun provideFactory(app: Application,todoRepo: TodoRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                        return TaskViewModel(app,todoRepo) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}
