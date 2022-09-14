package com.andreirookie.essentialcookings.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andreirookie.essentialcookings.db.RecipeDao
import com.andreirookie.essentialcookings.steps.Step
import java.lang.IndexOutOfBoundsException
import java.util.*

class RecipeRepositorySQLiteImpl(
    private val dao : RecipeDao
) : RecipeRepository {

    private var recipes = listOf<Recipe>()
    private val data = MutableLiveData(recipes)
    init {
        recipes = dao.getAll()
        data.value = recipes
    }

    override fun getAll(): LiveData<List<Recipe>> {
        return data
    }

    // Favorites
    //private var favoriteRecipes = recipes.filter { it.isFavorite }
    //если без init - драг дроп не работает нормально в Favorites
    private var favoriteRecipes = listOf<Recipe>()
    init {
        favoriteRecipes = recipes.filter { it.isFavorite }
    }
//    private val favoriteRecipesData = MutableLiveData(favoriteRecipes)
//    override fun getAllFavorites(): LiveData<List<Recipe>> {
//        return favoriteRecipesData
//    }
    private val favoriteRecipesData = MutableLiveData(favoriteRecipes)
    override fun getAllFavorites(): LiveData<List<Recipe>> {
        return favoriteRecipesData
    }

    // Filter by category
    private var notFilteredByCategoryRecipes = recipes

    // Drag & drop
    override fun swap(fromPosition: Int, toPosition: Int) {
        try {
            // синхронный своп
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

            favoriteRecipesData.value = favoriteRecipes
            data.value = recipes

    }


    override fun makeFavoriteById(recipeId: Long) {

        dao.makeFavoriteById(recipeId)

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

        dao.removeById(recipeId)

        recipes = recipes.filter { it.id != recipeId }

        // Filter by category
        notFilteredByCategoryRecipes = notFilteredByCategoryRecipes.filter { it.id != recipeId }

        favoriteRecipes = recipes.filter { it.isFavorite }
        favoriteRecipesData.value = favoriteRecipes
        data.value = recipes
    }

    override fun saveRecipe(recipe: Recipe) {

        val saved = dao.saveRecipe(recipe)

        recipes = if (recipe.id == 0L) {
            listOf(saved) + recipes
        } else {
            recipes.map {
                if (it.id != recipe.id) it else saved
            }
        }

        //  Filter by category
        notFilteredByCategoryRecipes = if (recipe.id == 0L) {
            listOf(saved) + notFilteredByCategoryRecipes
        } else {
            notFilteredByCategoryRecipes.map {
                if (it.id != recipe.id) it else saved
            }
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

        dao.addMainImage(recipeId, uri)

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

    //Steps
    override fun getAllRecipeSteps(recipeId: Long): LiveData<List<Step>> {
        val recipe = recipes.find {
            it.id == recipeId
        }
        val steps = recipe?.steps ?: emptyList()
        return MutableLiveData(steps)
    }
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

}