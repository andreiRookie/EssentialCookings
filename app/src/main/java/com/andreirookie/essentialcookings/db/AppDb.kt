package com.andreirookie.essentialcookings.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.andreirookie.essentialcookings.db.RecipeColumns.DDL

class AppDb private constructor(db: SQLiteDatabase) {
    val recipeDao: RecipeDao = RecipeDaoImpl(db)

    companion object {

        @Volatile
        private  var instance: AppDb? = null
        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDatabase(context, arrayOf(DDL))
                ).also { instance = it }
            }

        }

        private fun buildDatabase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs
        ).writableDatabase
    }
}