package com.andreirookie.essentialcookings.steps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.andreirookie.essentialcookings.data.RecipeRepository
import com.andreirookie.essentialcookings.data.RecipeRepositorySQLiteImpl
import com.andreirookie.essentialcookings.db.AppDb
import com.andreirookie.essentialcookings.util.SingleLiveEvent

class StepViewModel(application: Application) : AndroidViewModel(application) {

    //private val repository: RecipeRepository = RecipeRepositoryInMemoryImpl()
    private val repository: RecipeRepository = RecipeRepositorySQLiteImpl(
        AppDb.getInstance(application).recipeDao
    )

    private var tempSteps = StepsFileRepository(application)
    val stepsData = { recipeId: Long ->
        repository.getAllRecipeSteps(recipeId)
    }
//      Edit step
//    private val emptyStep = Step(
//        image = "", content = ""
//    )
//    var newStepData = MutableLiveData(emptyStep)

    val navigateToNewStepFragEvent = SingleLiveEvent<Long>()
    fun startAddingStep(recipeId: Long) {
        navigateToNewStepFragEvent.value = recipeId
    }
    fun saveStep(step: Step) {
        tempSteps.addStep(step)
    }

    fun getTempSteps(): List<Step> = tempSteps.getSteps()

    fun deleteTempSteps() = tempSteps.clearFile()

}