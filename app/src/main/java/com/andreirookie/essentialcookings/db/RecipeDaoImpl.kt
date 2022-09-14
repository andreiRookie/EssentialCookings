package com.andreirookie.essentialcookings.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.lifecycle.LiveData
import com.andreirookie.essentialcookings.data.Recipe

class RecipeDaoImpl(private val db: SQLiteDatabase) : RecipeDao {

    override fun getAll(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        db.query(
            RecipeColumns.TABLE,
            RecipeColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${RecipeColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                recipes.add(map(it))
            }
        }
        return recipes
    }

//    override fun getAllFavorites(): List<Recipe> {
//        val favoriteRecipes = mutableListOf<Recipe>()
//        db.query(
//            RecipeColumns.TABLE_FAVORITE,
//            RecipeColumns.ALL_COLUMNS,
//            null,
//            null,
//            null,
//            null,
//            "${RecipeColumns.COLUMN_ID} DESC"
//        ).use {
//            while (it.moveToNext()) {
//                favoriteRecipes.add(map(it))
//            }
//        }
//        return favoriteRecipes
//    }

//    override fun swap(fromPosition: Int, toPosition: Int) {
//        TODO("Not yet implemented")
//    }

    override fun makeFavoriteById(recipeId: Long) {
        db.execSQL(
            """
                UPDATE recipes SET
                    isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
                WHERE id = ?;
            """.trimIndent(),
            arrayOf(recipeId)
        )

        // ??? makeFavoriteById(recipeId: Long, ????isFavorite: Boolean???)
//        val values = ContentValues().apply {
//            put(RecipeColumns.COLUMN_IS_FAVORITE, ????)
//        }
//        db.update(RecipeColumns.TABLE,
    //        values,
    //        "${RecipeColumns.COLUMN_ID} = ?",
//            arrayOf(recipeId.toString()))

    }

    override fun addMainImage(recipeId: Long, uri: String) {
//        db.execSQL(
//            """
//                UPDATE recipes SET
//                    image = uri
//                WHERE id = ?;
//            """.trimIndent(),
//            arrayOf(recipeId)
//        )

        val values = ContentValues().apply {
         //   put(RecipeColumns.COLUMN_ID, recipeId)
            put(RecipeColumns.COLUMN_IMAGE, uri)
        }
//        db.replace(
//            RecipeColumns.TABLE,
//            null,
//            values
//        )
        db.update(
            RecipeColumns.TABLE,
            values,
            "${RecipeColumns.COLUMN_ID} = ?",
            arrayOf(recipeId.toString()))
    }

    override fun removeById(recipeId: Long) {
        db.delete(
            RecipeColumns.TABLE,
            "${RecipeColumns.COLUMN_ID} = ?",
            arrayOf(recipeId.toString())
        )
    }

    override fun saveRecipe(recipe: Recipe): Recipe {
        val values = ContentValues().apply {
            if (recipe.id != 0L) {
                put(RecipeColumns.COLUMN_ID, recipe.id)
            }
            put(RecipeColumns.COLUMN_TITLE, recipe.title)
            put(RecipeColumns.COLUMN_CATEGORY, recipe.category)
            put(RecipeColumns.COLUMN_AUTHOR, recipe.author)
            put(RecipeColumns.COLUMN_IS_FAVORITE, recipe.isFavorite)
            put(RecipeColumns.COLUMN_IMAGE, recipe.image)
        }
        val id = db.replace(RecipeColumns.TABLE, null, values)
        db.query(
            RecipeColumns.TABLE,
            RecipeColumns.ALL_COLUMNS,
            "${RecipeColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

//    override fun applyFilterByCategory(category: String) {
//        TODO("Not yet implemented") ???
//    }
//
//    override fun cancelFilterByCategory(category: String) {
//        TODO("Not yet implemented") ???
//    }

    private fun map(cursor: Cursor): Recipe {
        with(cursor) {
            return Recipe(
                id = getLong(getColumnIndexOrThrow(RecipeColumns.COLUMN_ID)),
                title = getString(getColumnIndexOrThrow(RecipeColumns.COLUMN_TITLE)),
                category = getString(getColumnIndexOrThrow(RecipeColumns.COLUMN_CATEGORY)),
                author = getString(getColumnIndexOrThrow(RecipeColumns.COLUMN_AUTHOR)),
                isFavorite = getInt(getColumnIndexOrThrow(RecipeColumns.COLUMN_IS_FAVORITE)) != 0,
                image = getString(getColumnIndexOrThrow(RecipeColumns.COLUMN_IMAGE))
//                image = null
            )
        }
    }
}