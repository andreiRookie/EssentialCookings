package com.andreirookie.essentialcookings.data

import android.net.Uri
import androidx.lifecycle.LiveData
import com.andreirookie.essentialcookings.steps.Step

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

    // steps
    fun getAllRecipeSteps(recipeId: Long): LiveData<List<Step>>
    fun addStep(recipeId: Long,step: Step)

    // Search through title
    fun searchThroughTitle(query: String?)

}