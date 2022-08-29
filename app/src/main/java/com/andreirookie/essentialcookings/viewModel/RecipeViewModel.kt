package com.andreirookie.essentialcookings.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.data.RecipeRepository
import com.andreirookie.essentialcookings.data.RecipeRepositoryInMemoryImpl
import com.andreirookie.essentialcookings.util.SingleLiveEvent

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository = RecipeRepositoryInMemoryImpl()

    private val emptyRecipe = Recipe(
        id = 0L,
        title = "Всякая вкуснятина",
        author = "Некто",
        category = "Русская кухня"
    )

    var editedRecipe = MutableLiveData(emptyRecipe)

    val data = repository.getAll()


    // Add Recipe
    val navigateToNewRecipeFragEvent = SingleLiveEvent<Unit>()
    fun addRecipe() {
        navigateToNewRecipeFragEvent.call()
    }

    // Change and save recipe
    fun changeAndSaveRecipe(recipe: Recipe) {
        editedRecipe.value?.let {
            if (it == recipe) {
                return
            }
            repository.saveRecipe(recipe)
        }
        editedRecipe.value = emptyRecipe
    }

    // Edit recipe
    val navigateToEditRecipeFragEvent = SingleLiveEvent<Recipe>()

    fun edit(recipe: Recipe) {
        editedRecipe.value = recipe
    }

    fun editRecipe(recipe: Recipe) {
        navigateToEditRecipeFragEvent.value = recipe
    }


    // Remove recipe
    fun remove(recipeId: Long) = repository.removeById(recipeId)

    // Make recipe Favorite

}