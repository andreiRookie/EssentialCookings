package com.andreirookie.essentialcookings.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.andreirookie.essentialcookings.R
import com.andreirookie.essentialcookings.databinding.ActivityAppBinding
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_Kookings)

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)


        // Fragment names in AppBar
        val appBarConfig = AppBarConfiguration(setOf(
            R.id.fragmentFeed,
            R.id.fragmentFilters,
            R.id.fragmentFavorites)
        )
        setupActionBarWithNavController(navController, appBarConfig)


    }
}