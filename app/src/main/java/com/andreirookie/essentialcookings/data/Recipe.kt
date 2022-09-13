package com.andreirookie.essentialcookings.data

import android.net.Uri
import com.andreirookie.essentialcookings.steps.Step
import java.io.Serializable

data class Recipe(
    val id: Long,
    val title: String,
    val category: String,
    val author: String,
    val isFavorite: Boolean = false,
    val image: String? = null,

    val steps: List<Step>? = null


//    val description: String
//    val steps: String(list?? array?? json??),

)  : Serializable {
}