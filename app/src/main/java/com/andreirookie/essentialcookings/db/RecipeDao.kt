package com.andreirookie.essentialcookings.db

import android.net.Uri
import androidx.lifecycle.LiveData
import com.andreirookie.essentialcookings.data.Recipe

interface RecipeDao {

    // Drag & drop
//    fun swap(fromPosition: Int, toPosition: Int)

    fun getAll(): List<Recipe>

    fun makeFavoriteById(recipeId: Long)

    fun removeById(recipeId: Long)

    fun saveRecipe(recipe: Recipe): Recipe

    // Add main image
    fun addMainImage(recipeId: Long, uri: String)

    // Favorite recipes
//    fun getAllFavorites(): List<Recipe>

    // Filter
//    fun applyFilterByCategory(category: String)
//    fun cancelFilterByCategory(category: String)


}