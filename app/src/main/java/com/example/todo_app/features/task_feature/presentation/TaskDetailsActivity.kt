package com.example.todo_app.features.task_feature.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.todo_app.R
import com.example.todo_app.databinding.ActivityTaskDetailsBinding
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.presentation.adaptors.formatDate
import com.example.todo_app.utils.constants.nav

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var detailsBinding: ActivityTaskDetailsBinding
    private lateinit var todoItemEntity: TodoItemEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(detailsBinding.root)
        todoItemEntity = intent.getParcelableExtra(nav.DETAILED_TODO_ENTITY)!!
        todoItemEntity.let {
            Glide.with(this)
                .load(Uri.parse(it.imageLink))
                .placeholder(R.drawable.round_square_place_holder_icon)
                .error(R.drawable.error_icon)
                .into(detailsBinding.image)
            detailsBinding.title.text = it.title
            detailsBinding.subTitle.text = it.subTitle
            detailsBinding.priorityText.setText(it.priority)
            detailsBinding.statusText.setText(it.status)
            detailsBinding.dateText.setText(it.date.formatDate())

        }


    }

    fun returnToHomeScreen(view: View)  = finish()
}