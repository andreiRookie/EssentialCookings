package com.andreirookie.essentialcookings.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
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

//        val actionBar = this.actionBar
//        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    // ActionBar back home button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                Toast.makeText(this, "OnBAckPressed Works", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}