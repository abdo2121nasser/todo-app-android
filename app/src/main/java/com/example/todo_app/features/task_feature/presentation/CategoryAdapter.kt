package com.example.todo_app.features.task_feature.presentation

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.databinding.CategoryItemBinding

class CategoryAdapter(
    private val activity: HomeTaskActivity,
    private val categories: List<String>,

    ) : RecyclerView.Adapter<CategoryAdapter.Holder>() {
    private var selectedIndex: Int = 0

    class Holder(item: CategoryItemBinding) : RecyclerView.ViewHolder(item.root) {
        val textView = item.text
        var categoryContainer: CardView = item.categoryItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        LayoutInflater.from(parent.context),parent,false)

        val binding: CategoryItemBinding =
            CategoryItemBinding.inflate(activity.layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = categories.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val isSelected = position.toInt() == selectedIndex

        holder.textView.text = categories[position]
        val bgColor = if (isSelected) R.color.vilote else R.color.light_vilote
        val textColor = if (isSelected) R.color.white else R.color.grey

        holder.categoryContainer.setCardBackgroundColor(ContextCompat.getColor(activity, bgColor))
        holder.textView.setTextColor(ContextCompat.getColor(activity, textColor))

        holder.categoryContainer.setOnClickListener {
            if (position != selectedIndex) {
                notifyItemChanged(selectedIndex)//prev index
                selectedIndex = position.toInt()
                notifyItemChanged(selectedIndex)//new position
            }
        }


    }
}