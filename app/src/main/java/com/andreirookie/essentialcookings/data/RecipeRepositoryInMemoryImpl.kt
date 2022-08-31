package com.andreirookie.essentialcookings.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class RecipeRepositoryInMemoryImpl : RecipeRepository {

    private var uniqueId = 0L

    private var recipes = listOf<Recipe>()
    init {
        repeat(10) {
            val recipe = Recipe(
                id = uniqueId + 1L,
                title = "${uniqueId + 1}: блины такие блины сякие, эдакие",
                category = "Русская кухня",
                author = "Андрей",
            )
            uniqueId++
            recipes += recipe
        }
    }
    private val data = MutableLiveData(recipes)


    // Drag & drop
    override fun swap(fromPosition: Int, toPosition: Int) {
        recipes = recipes.apply {  Collections.swap(this,fromPosition,toPosition) }
        data.value = recipes
    }

    override fun getAll(): LiveData<List<Recipe>> {
        return data
    }

    override fun makeFavoriteById(recipeId: Long) {
        recipes = recipes.map {
            if (it.id != recipeId) it else
                run {
                    if (it.isFavorite) it.copy(isFavorite = !it.isFavorite)
                    else it.copy(isFavorite = !it.isFavorite)
                }
        }
        data.value = recipes
    }

    override fun removeById(recipeId: Long) {
        recipes = recipes.filter { it.id != recipeId }
        data.value = recipes
    }

    override fun saveRecipe(recipe: Recipe) {
        if (recipe.id == 0L) {
            recipes = listOf(
                recipe.copy(
                    id = ++uniqueId,
                    title = "${uniqueId}: ${recipe.title}",
                    category = recipe.category,
                    author = recipe.author
                )
            ) + recipes
        }
        recipes = recipes.map {
            if (it.id != recipe.id) it else it.copy(
                title = recipe.title,
                category = recipe.category,
                author = recipe.author)
        }
        data.value = recipes
    }

}