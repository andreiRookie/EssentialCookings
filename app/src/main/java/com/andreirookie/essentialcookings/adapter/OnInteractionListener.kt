package com.andreirookie.essentialcookings.adapter

import com.andreirookie.essentialcookings.data.Recipe

interface OnInteractionListener {

    fun onRemove(recipe: Recipe) {}
    fun onFavorite(recipe:Recipe) {}
    fun onEdit(recipe: Recipe) {}

    fun onAddImage(recipe: Recipe) {}

    fun onBinding(recipe: Recipe)

}