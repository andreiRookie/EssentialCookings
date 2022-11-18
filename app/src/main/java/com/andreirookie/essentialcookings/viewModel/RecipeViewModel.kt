package com.andreirookie.essentialcookings.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.data.RecipeRepository
import com.andreirookie.essentialcookings.data.RecipeRepositoryInMemoryImpl
import com.andreirookie.essentialcookings.data.RecipeRepositorySQLiteImpl
import com.andreirookie.essentialcookings.db.AppDb
import com.andreirookie.essentialcookings.util.SingleLiveEvent

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    //private val repository: RecipeRepository = RecipeRepositoryInMemoryImpl()
    private val repository: RecipeRepository = RecipeRepositorySQLiteImpl(
        AppDb.getInstance(application).recipeDao
    )

    private val emptyRecipe = Recipe(
        id = 0L,
        title = "Всякая вкуснятина",
        author = "Некто",
        category = "Русская кухня"
    )

    var editedRecipe = MutableLiveData(emptyRecipe)

    val data = repository.getAll()

    // Show only favorite recipes
    val favoriteRecipesData = repository.getAllFavorites()
    // Make a recipe Favorite
    fun favorite(recipeId: Long) = repository.makeFavoriteById(recipeId)

    // Drag & drop
    fun swap(fromPos: Int, toPos: Int) {
        repository.swap(fromPos, toPos)
    }

    // Add a Recipe
    val navigateToNewRecipeFragEvent = SingleLiveEvent<Unit>()
    fun addRecipe() {
        navigateToNewRecipeFragEvent.call()
    }

    // Change and save a recipe
    fun changeAndSaveRecipe(recipe: Recipe) {
        editedRecipe.value?.let {
            if (it == recipe) {
                return
            }
            repository.saveRecipe(recipe)
        }
        editedRecipe.value = emptyRecipe
    }

    // Edit a recipe
    val navigateToEditRecipeFragEvent = SingleLiveEvent<Recipe>()
    fun edit(recipe: Recipe) {
        editedRecipe.value = recipe
    }
    fun editRecipe(recipe: Recipe) {
        navigateToEditRecipeFragEvent.value = recipe
    }

    // Remove a recipe
    fun remove(recipeId: Long) = repository.removeById(recipeId)


    // Filter by category
    fun filter(category: String) {
        repository.applyFilterByCategory(category)
    }
    fun cancelFilter(category: String) {
        repository.cancelFilterByCategory(category)
    }

    //Add image
    val addingImageEvent = SingleLiveEvent<Recipe>()
    val addingImageUriEvent = SingleLiveEvent<String>()
    fun startAddingImage(recipe: Recipe) {
        addingImageEvent.value = recipe
    }
    fun addImageUri(uri: String) {
        addingImageUriEvent.value = uri
    }
    fun addImage(recipeId: Long, uri: String) {
        println("viewModel.addImage(${recipeId}, $uri)")
        repository.addMainImage(recipeId, uri)
    }

    // Single Recipe Fragment
    val navigateToSingleRecipeFragmentEvent = SingleLiveEvent<Recipe>()
    fun navigateToSingleRecipeFragment(recipe: Recipe) {
        navigateToSingleRecipeFragmentEvent.value = recipe
    }

}