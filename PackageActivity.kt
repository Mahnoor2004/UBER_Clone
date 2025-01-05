package com.example.project_uber.com.example.project_uber

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uber.R

class PackageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)

        // Find the back arrow view by its ID and set a click listener
        val backArrow = findViewById<View>(R.id.backArrow)
        backArrow.setOnClickListener {
            // Finish the current activity and return to the previous one
            finish()
        }
    }
}
