package com.andreirookie.essentialcookings.util

import android.os.Bundle
import com.andreirookie.essentialcookings.data.Recipe
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// DELEGATE
object RecipeArg: ReadWriteProperty<Bundle, Recipe> {

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Recipe) {
        thisRef.putSerializable(property.name, value)
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): Recipe {
        return thisRef.getSerializable(property.name) as Recipe
    }

}