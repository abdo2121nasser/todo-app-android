package com.example.todo_app.task_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityHomeTaskBinding
import com.example.todo_app.databinding.TodoItemBinding
import com.example.todo_app.utils.Constants

typealias ui = Constants.UiStrings

class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private val categories: List<String> = listOf(ui.ALL, ui.IN_PROGRESS, ui.WAITING, ui.FINISH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.categoryRecycleView.adapter = CategoryAdapter(this, categories)
        val dummyData = listOf("Task 1", "Task 2", "Task 3")
        homeBinding.todoRecycleView.adapter = DummyAdapter()


    }


}

class DummyAdapter() :
    RecyclerView.Adapter<DummyAdapter.Holder>() {

    class Holder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }

    override fun getItemCount(): Int = 5
}