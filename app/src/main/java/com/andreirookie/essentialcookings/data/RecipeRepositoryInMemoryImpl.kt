package com.andreirookie.essentialcookings.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andreirookie.essentialcookings.steps.Step
import java.lang.IndexOutOfBoundsException
import java.util.*

class RecipeRepositoryInMemoryImpl : RecipeRepository {

    private var uniqueId = 0L

    private var recipes = listOf<Recipe>()
    init {
        repeat(5) {
            val recipe = Recipe(
                id = uniqueId + 1L,
                title = "${uniqueId + 1}: блины такие блины сякие, эдакие",
                category = Cuisines.RUSSIAN.title,
                author = "Андрей",
            )
            uniqueId++
            recipes += recipe
        }
    }
    private val data = MutableLiveData(recipes)
    override fun getAll(): LiveData<List<Recipe>> {
        return data
    }

    // Favorites
    private var favoriteRecipes = listOf<Recipe>()
    private val favoriteRecipesData = MutableLiveData(favoriteRecipes)
    override fun getAllFavorites(): LiveData<List<Recipe>> {
        return favoriteRecipesData
    }

    // Filter by category
    private var notFilteredByCategoryRecipes = recipes

    // Drag & drop
    override fun swap(fromPosition: Int, toPosition: Int) {
        try {
            recipes = recipes.apply { Collections.swap(this, fromPosition, toPosition) }

            favoriteRecipes =
                favoriteRecipes.apply { Collections.swap(this, fromPosition, toPosition) }

            // Filter by category
            notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.apply {
                Collections.swap(
                    this,
                    fromPosition,
                    toPosition
                )
            }
        } catch (e: IndexOutOfBoundsException) {}

//        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }

    override fun makeFavoriteById(recipeId: Long) {
        recipes = recipes.map {
            if (it.id != recipeId) it else
                run {
                    if (it.isFavorite) it.copy(isFavorite = !it.isFavorite)
                    else it.copy(isFavorite = !it.isFavorite)
                }
        }
        // Filter by category
        notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.map {
            if (it.id != recipeId) it else
                run {
                    if (it.isFavorite) it.copy(isFavorite = !it.isFavorite)
                    else it.copy(isFavorite = !it.isFavorite)
                }
        }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }

    override fun removeById(recipeId: Long) {
        recipes = recipes.filter { it.id != recipeId }

        // Filter by category
        notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.filter { it.id != recipeId }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
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

            //  Filter by category
            notFilteredByCategoryRecipes = listOf(
                recipe.copy(
                    id = ++uniqueId,
                    title = "${uniqueId}: ${recipe.title}",
                    category = recipe.category,
                    author = recipe.author
                )
            ) + notFilteredByCategoryRecipes

            favoriteRecipes = recipes.filter { it.isFavorite }
            favoriteRecipesData.value = favoriteRecipes
            data.value = recipes
            return
        }
        recipes = recipes.map {
            if (it.id != recipe.id) it else it.copy(
                title = recipe.title,
                category = recipe.category,
                author = recipe.author)
        }
        // Filter by category
        notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.map {
            if (it.id != recipe.id) it else it.copy(
                title = recipe.title,
                category = recipe.category,
                author = recipe.author)
        }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }

    // Filter by category
    override fun applyFilterByCategory(category: String) {
        recipes = recipes.filter { it.category != category }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }
    override fun cancelFilterByCategory(category: String) {
        recipes = recipes + notFilteredByCategoryRecipes.filter { it.category == category }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }

    // Add Image
    override fun addMainImage(recipeId: Long, uri: String) {
        recipes = recipes.map {
            if (it.id != recipeId) it else it.copy(image = uri)
        }
        // Filter by category
        notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.map {
            if (it.id != recipeId) it else it.copy(image = uri)
        }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }

    // Add step
    override fun addStep(recipeId: Long, step: Step) {
        recipes = recipes.map {
            if (it.id != recipeId) it else it.copy(
                steps = (listOf(step) + it.steps)
            )
        }

        notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.map {
            if (it.id != recipeId) it else it.copy(
                steps = (listOf(step) + it.steps)
            )
        }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }
    override fun getAllRecipeSteps(recipeId: Long): LiveData<List<Step>> {
        val recipe = recipes.find {
            it.id == recipeId
        }
        val steps = recipe?.steps ?: emptyList()
        return MutableLiveData(steps)
    }

        //Search
        override fun searchThroughTitle(query: String?) {
            if (query != null) {
                val foundRecipes = recipes.filter {
                    it.title.contains(query, ignoreCase = true) }
                data.value = foundRecipes
            } else {

                data.value = recipes
            }

        }
}