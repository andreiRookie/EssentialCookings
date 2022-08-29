package com.andreirookie.essentialcookings.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RecipeRepositoryInMemoryImpl : RecipeRepository {

    private var uniqueId = 0L

    private var recipes = listOf<Recipe>()
    init {
        repeat(30) {
            val recipe = Recipe(
                id = uniqueId + 1L,
                title = "${uniqueId + 1}: блины такие блины сякие, эдакие",
                author = "Андрей",
                category = "Русская кухня"
            )
            uniqueId++
            recipes += recipe
        }
    }
    private val data = MutableLiveData(recipes)

    override fun getAll(): LiveData<List<Recipe>> {
        return data
    }

    override fun makeFavoriteById(recipeId: Long) {
        TODO("Not yet implemented")
    }

    override fun removeById(recipeId: Long) {
        TODO("Not yet implemented")
    }

    override fun saveRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

}