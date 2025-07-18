package com.example.todo_app.features.task_feature.presentation.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.databinding.CategoryItemBinding

class CategoryAdapter(
    private val context: Context,
    private val categories: List<String>,

    ) : RecyclerView.Adapter<CategoryAdapter.Holder>() {
    var selectedIndex = MutableLiveData<Int>(0)

    class Holder(item: CategoryItemBinding) : RecyclerView.ViewHolder(item.root) {
        val textView = item.text
        var categoryContainer: CardView = item.categoryItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        LayoutInflater.from(parent.context),parent,false)

        val binding: CategoryItemBinding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = categories.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val isSelected = position.toInt() == selectedIndex.value

        holder.textView.text = categories[position]
        val bgColor = if (isSelected) R.color.vilote else R.color.light_vilote
        val textColor = if (isSelected) R.color.white else R.color.grey

        holder.categoryContainer.setCardBackgroundColor(context.getColor(bgColor))
        holder.textView.setTextColor(context.getColor(textColor))

        holder.categoryContainer.setOnClickListener {
            if (position != selectedIndex.value) {
                selectedIndex.value?.let { it1 -> notifyItemChanged(it1) }//prev index
                selectedIndex.value = position
                selectedIndex.value?.let { it1 -> notifyItemChanged(it1) }//new position
            }
        }


    }
}