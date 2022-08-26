package com.andreirookie.essentialcookings.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreirookie.essentialcookings.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_EssentialCookings)
        setContentView(R.layout.activity_main)
    }
}