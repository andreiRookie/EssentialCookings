package com.andreirookie.essentialcookings.adapter

import androidx.recyclerview.widget.DiffUtil
import com.andreirookie.essentialcookings.data.Recipe

class RecipeDiffCallBack : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

}