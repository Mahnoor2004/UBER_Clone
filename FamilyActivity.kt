package com.example.project_uber

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView

class FamilyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family) // Set your layout file here

        // Enable the back button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set up the back button listener
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            // Close the FamilyActivity and return to the previous activity
            finish()
        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Navigate back to AccountFragment
                finish() // This will close FamilyActivity and return to previous activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
