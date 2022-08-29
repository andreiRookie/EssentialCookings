package com.andreirookie.essentialcookings.data

import java.io.Serializable

data class Recipe(
    val id: Long,
    val title: String,
    val category: String,
    val author: String,
    val isFavorite: Boolean = false,
//    val description: String
//    val steps: String(array?? json??),
//    val image: String(uri)
)  : Serializable {
}