package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Make sure this is the correct layout file

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Load the default fragment (HomeFragment) when the activity is created
        loadFragment(HomeFragment())

        // Handle navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> fragment = HomeFragment()
                R.id.navigation_services -> fragment = ServicesFragment()
                R.id.navigation_activity -> fragment = ActivityFragment()
                R.id.navigation_account -> fragment = AccountFragment()
            }
            fragment?.let { loadFragment(it) }
            true
        }
    }
    private fun loadFragment(fragment: Fragment) {
        // Replaces the fragment in the fragment_container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
