
package com.example.e_commerce_kot.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.e_commerce_kot.Cart
import com.example.e_commerce_kot.Home
import com.example.e_commerce_kot.Profile
import com.example.e_commerce_kot.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the initial fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, Home()) // Home is your default fragment
            .commit()

        // BottomNavigationView setup
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.home -> Home()
                R.id.profile -> Profile()
                R.id.cart -> Cart()
                else -> Home() // Fallback to Home
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, selectedFragment)
                .commit()
            true
        }
    }
}
