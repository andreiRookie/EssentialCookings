package com.andreirookie.essentialcookings.app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.andreirookie.essentialcookings.app.data.Recipe
import com.andreirookie.essentialcookings.app.data.RecipeRepository
import com.andreirookie.essentialcookings.app.data.RecipeRepositoryInMemoryImpl

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val emptyRecipe = Recipe(
        id = 0L,
        title = "Всякая вкуснятина",
        author = "Некто",
        category = "русская кухня"
    )

    private val repository: RecipeRepository = RecipeRepositoryInMemoryImpl()

    val data = repository.getAll()
}