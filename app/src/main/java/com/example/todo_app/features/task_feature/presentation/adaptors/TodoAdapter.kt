package com.example.todo_app.features.task_feature.presentation.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todo_app.R
import com.example.todo_app.databinding.TodoItemBinding
import com.example.todo_app.features.task_feature.data.entities.TodoItemEntity
import com.example.todo_app.features.task_feature.presentation.HomeTaskActivity
import com.example.todo_app.utils.Constants
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Locale
typealias ui=Constants.UiStrings

class TodoAdapter(private val activity: HomeTaskActivity, private val todoItems: List<TodoItemEntity>) :
    RecyclerView.Adapter<TodoAdapter.Holder>() {

    class Holder(item: TodoItemBinding) : RecyclerView.ViewHolder(item.root) {
        val image: ImageView = item.image
        val title: TextView = item.title
        val subTitle: TextView = item.subTitle
        val date: TextView = item.date
        val status:TextView=item.currentState
        val statusContainer:MaterialCardView=item.stateContainer

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
        holder.date.text = formatDate(todoItems[position].date)
       holder.status.text=todoItems[position].status
        holder.status.setTextColor(activity.getColor(chooseTextColor(holder.status.text.toString())))
        holder.statusContainer.setCardBackgroundColor(activity.getColor(chooseContainerColor(holder.status.text.toString())))
        Glide.with(activity)
            .load(todoItems[position].imageLink)
            .placeholder(R.drawable.round_square_place_holder_icon)
            .error(R.drawable.error_icon)
            .into(holder.image)


    }


    private fun  chooseTextColor(status:String):Int{
      return when(status.lowercase()){
           ui.IN_PROGRESS .lowercase()-> R.color.vilote
            ui.WAITING.lowercase()->R.color.orange
            else ->R.color.blue
        }
    }
    private fun  chooseContainerColor(status:String):Int{
      return when(status.lowercase()){
           ui.IN_PROGRESS .lowercase()-> R.color.light_vilote
            ui.WAITING.lowercase()->R.color.light_orange
            else ->R.color.light_blue
        }
    }

    private fun formatDate(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateStr)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateStr
        }
    }

}