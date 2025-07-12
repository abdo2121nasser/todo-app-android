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
import com.example.todo_app.task_feature.data.TodoItemEntity
import com.example.todo_app.utils.Constants

typealias ui = Constants.UiStrings
val image="https://tse4.mm.bing.net/th/id/OIP.ifdAa4wGncxAc8EBmXqJigHaH5?r=0&rs=1&pid=ImgDetMain&o=7&rm=3"
class HomeTaskActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeTaskBinding
    private val categories: List<String> = listOf(ui.ALL, ui.IN_PROGRESS, ui.WAITING, ui.FINISH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeTaskBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        homeBinding.categoryRecycleView.adapter = CategoryAdapter(this, categories)
        val dummyData = listOf("Task 1", "Task 2", "Task 3")
        homeBinding.todoRecycleView.adapter = TodoAdapter(this, listOf(
            TodoItemEntity("ggu"+image+"//5556khh","sds","sds","30/5/2052"),
            TodoItemEntity(image,"sds","sds","30/5/2052"),
            TodoItemEntity(image,"sds","sds","30/5/2052")
        ))


    }


}

//class DummyAdapter() :
//    RecyclerView.Adapter<DummyAdapter.Holder>() {
//
//    class Holder( binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val binding =
//            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return Holder(binding)
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//    }
//
//    override fun getItemCount(): Int = 5
//}