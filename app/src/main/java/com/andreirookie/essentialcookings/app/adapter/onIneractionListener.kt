package com.andreirookie.essentialcookings.app.adapter

import com.andreirookie.essentialcookings.app.data.Recipe

interface onIneractionListener {

    fun onRemove(recipe: Recipe) {}
    fun onFavorite(recipe:Recipe) {}
    fun onEdit(recipe: Recipe) {}
}