package com.andreirookie.essentialcookings.data

import androidx.lifecycle.LiveData

interface RecipeRepository {

    // Drag & drop
    fun swap(fp: Int, tp: Int)

    fun getAll(): LiveData<List<Recipe>>

    fun makeFavoriteById(recipeId: Long)

    fun removeById(recipeId: Long)

    fun saveRecipe(recipe: Recipe)

}