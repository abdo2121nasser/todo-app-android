package com.example.todo_app.task_feature.presentation

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.todo_app.R
import com.example.todo_app.databinding.TodoItemBinding
import com.example.todo_app.task_feature.data.TodoItemEntity

class TodoAdapter(private val activity: HomeTaskActivity, private val todoItems: List<TodoItemEntity>) :
    RecyclerView.Adapter<TodoAdapter.Holder>() {

    class Holder(item: TodoItemBinding) : RecyclerView.ViewHolder(item.root) {
        val image: ImageView = item.image
        val title: TextView = item.title
        val subTitle: TextView = item.subTitle
        val date: TextView = item.date

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = todoItems.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = todoItems[position].title
        holder.subTitle.text = todoItems[position].subTitle
        holder.date.text = todoItems[position].date
        Glide.with(activity)
            .load(todoItems[position].imageLink)
            .placeholder(R.drawable.round_square_place_holder_icon)
            .error(R.drawable.error_icon)
            .into(holder.image)


    }


}