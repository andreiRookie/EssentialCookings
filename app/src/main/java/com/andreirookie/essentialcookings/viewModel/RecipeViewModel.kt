package com.andreirookie.essentialcookings.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.data.RecipeRepository
import com.andreirookie.essentialcookings.data.RecipeRepositoryInMemoryImpl
import com.andreirookie.essentialcookings.util.SingleLiveEvent

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val emptyRecipe = Recipe(
        id = 0L,
        title = "Всякая вкуснятина",
        author = "Некто",
        category = "русская кухня"
    )

    private val repository: RecipeRepository = RecipeRepositoryInMemoryImpl()

    val data = repository.getAll()


    // Add Recipe
    val navigateToNewRecipeFragEvent = SingleLiveEvent<Unit>()
    fun addRecipe() {
        navigateToNewRecipeFragEvent.call()
    }

    // Edit recipe


    // Remove recipe


    // Make recipe Favorite

}