package com.andreirookie.essentialcookings.data

import android.net.Uri
import java.io.Serializable

data class Recipe(
    val id: Long,
    val title: String,
    val category: String,
    val author: String,
    val isFavorite: Boolean = false,
    val image: Uri? = null
//    val description: String
//    val steps: String(array?? json??),

)  : Serializable {
}