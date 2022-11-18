package com.andreirookie.essentialcookings.data

import android.net.Uri
import androidx.lifecycle.LiveData

interface RecipeRepository {

    // Drag & drop
    fun swap(fromPosition: Int, toPosition: Int)

    fun getAll(): LiveData<List<Recipe>>

    fun makeFavoriteById(recipeId: Long)

    fun removeById(recipeId: Long)

    fun saveRecipe(recipe: Recipe)

    // Favorite recipes
    fun getAllFavorites(): LiveData<List<Recipe>>

    // Filter
    fun applyFilterByCategory(category: String)
    fun cancelFilterByCategory(category: String)

    // Add main image
    fun addMainImage(recipeId: Long, uri: String)

}