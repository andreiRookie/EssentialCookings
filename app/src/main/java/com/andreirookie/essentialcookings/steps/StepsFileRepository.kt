package com.andreirookie.essentialcookings.steps

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StepsFileRepository(private val context: Context) {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Step::class.java).type
    private val fileName = "steps.json"

    private var steps = listOf<Step>()
    init {
        val file = context.filesDir.resolve(fileName)
        if (file.exists()) {
            context.openFileInput(fileName).bufferedReader().use {
                steps = gson.fromJson(it, type)
            }
        } else {
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(steps))
        }
    }
}