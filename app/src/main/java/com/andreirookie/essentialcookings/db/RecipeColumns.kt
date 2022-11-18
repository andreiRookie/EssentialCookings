package com.andreirookie.essentialcookings.db

object RecipeColumns {

    const val TABLE = "recipes"
   // const val TABLE_FAVORITE = "favoriteRecipes"

    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_CATEGORY = "category"
    const val COLUMN_AUTHOR = "author"
    const val COLUMN_IS_FAVORITE = "isFavorite"
    const val COLUMN_IMAGE = "image"
    const val COLUMN_STEPS = "steps"

    val  ALL_COLUMNS = arrayOf(
         COLUMN_ID,
         COLUMN_TITLE,
         COLUMN_CATEGORY,
         COLUMN_AUTHOR,
         COLUMN_IS_FAVORITE,
         COLUMN_IMAGE,
         COLUMN_STEPS
    )

    val DDL = """
        CREATE TABLE $TABLE (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_TITLE TEXT NOT NULL,
        $COLUMN_CATEGORY TEXT NOT NULL,
        $COLUMN_AUTHOR TEXT NOT NULL,
        $COLUMN_IS_FAVORITE BOOLEAN NOT NULL DEFAULT false,
        $COLUMN_IMAGE TEXT,
        $COLUMN_STEPS TEXT NOT NULL
        );
    """.trimIndent()
}