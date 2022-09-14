package com.andreirookie.essentialcookings.db

import android.net.Uri
import androidx.lifecycle.LiveData
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.steps.Step

interface RecipeDao {

    // Drag & drop
//    fun swap(fromPosition: Int, toPosition: Int)

    fun getAll(): List<Recipe>

    fun makeFavoriteById(recipeId: Long)

    fun removeById(recipeId: Long)

    fun saveRecipe(recipe: Recipe): Recipe

    // Add main image
    fun addMainImage(recipeId: Long, uri: String)

    // Add step TODO
//    fun addStep(recipeId: Long,step: Step)

    // Favorite recipes -
// в БД не нужно т.к. используется для отображения на основе recipes
//    fun getAllFavorites(): List<Recipe>

    // Filter
//    fun applyFilterByCategory(category: String)
//    fun cancelFilterByCategory(category: String)


}