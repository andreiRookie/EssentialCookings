package com.andreirookie.essentialcookings.app.adapter

import androidx.recyclerview.widget.DiffUtil
import com.andreirookie.essentialcookings.app.data.Recipe

class RecipeDiffCallBack : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

}