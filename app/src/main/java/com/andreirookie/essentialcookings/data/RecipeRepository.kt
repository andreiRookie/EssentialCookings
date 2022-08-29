package com.andreirookie.essentialcookings.data

import androidx.lifecycle.LiveData

interface RecipeRepository {

    fun getAll(): LiveData<List<Recipe>>

    fun makeFavoriteById(recipeId: Long)

    fun removeById(recipeId: Long)

    fun saveRecipe(recipe: Recipe)

}