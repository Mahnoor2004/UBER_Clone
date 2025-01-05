package com.example.project_uber

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uber.databinding.ActivityReserveBinding

class ReserveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReserveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        binding = ActivityReserveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener on the back arrow to finish the activity
        binding.backArrow.setOnClickListener {
            finish() // This will close the current activity and return to the previous one
        }
    }
}
