package com.andreirookie.essentialcookings.steps

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StepsFileRepository(private val context: Context) {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Step::class.java).type
    private val typeString = TypeToken.getParameterized(String::class.java).type
    private val fileName = "steps.json"

    private var steps = listOf<Step>()
//    init {
//        val file = context.filesDir.resolve(fileName)
//        if (file.exists()) {
//            context.openFileInput(fileName).bufferedReader().use {
//                steps = gson.fromJson(it, type)
//            }
//        } else {
//            sync()
//        }
//
//    }

//    fun getStepsAsJson(): String {
//        val file = context.filesDir.resolve(fileName)
//        var stepsFile = String()
//        stepsFile = gson.toJson(steps)
//        if (file.exists()) {
//            context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
//                it.write(gson.toJson(stepsFile))
//            }

//            var ss = emptyList<Step>()
//            ss = gson.fromJson(stepsFile, type)
//        }
//        return stepsFile
//    }

//    fun getStepsFromJson(steps: String): List<Step> {
//        return gson.fromJson(steps, type)
//    }

    private fun sync() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(steps))
        }
    }

    fun getSteps(): List<Step> = steps

    fun addStep(step: Step) {
        steps = listOf(step) + steps
//        sync()
    }

    fun clearFile() {
        steps =  emptyList()
//        sync()
    }
}