package com.andreirookie.essentialcookings.data

data class Recipe(
    val id: Long,
    val title: String,
    val author: String,
    val category: String,
    val isFavorite: Boolean = false,
//    val description: String
//    val steps: String(array?? json??),
//    val image: String(uri)
) {
}