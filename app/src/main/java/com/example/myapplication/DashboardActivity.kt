package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Default Fragment
        loadFragment(CarsFragment())

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_cars -> {
                    loadFragment(CarsFragment())
                    true
                }
                R.id.navigation_types -> {
                    loadFragment(TypesFragment())
                    true
                }
                R.id.navigation_maps -> {
                    loadFragment(MapsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}