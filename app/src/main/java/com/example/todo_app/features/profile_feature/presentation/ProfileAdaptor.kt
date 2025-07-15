package com.example.todo_app.features.profile_feature.presentation

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.databinding.ProfileItemBinding
import com.example.todo_app.features.profile_feature.data.entities.ProfileItemEntity

class ProfileAdaptor(private val profileItems: List<ProfileItemEntity>) :
    RecyclerView.Adapter<ProfileAdaptor.Holder>() {


    class Holder(item: ProfileItemBinding) : RecyclerView.ViewHolder(item.root) {
         val title:TextView=item.title
         val subTitle:TextView=item.subTitle

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ProfileItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = profileItems.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text=profileItems[position].title
        holder.subTitle.text=profileItems[position].subTitle
    }


}