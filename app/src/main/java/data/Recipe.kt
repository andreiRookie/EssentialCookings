package data

data class Recipe(
    val recipeId: Long,
    val title: String,
    val author: String,
    val category: String,
    val isFavorite: Boolean,
//    val description: String
//    val steps: String(array?? json??),
//    val image: String(uri)
) {
}