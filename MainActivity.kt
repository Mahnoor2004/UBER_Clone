package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delay to mimic splash screen effect
        Handler(Looper.getMainLooper()).postDelayed({
            // Set the content view for the main activity after splash
            setContentView(R.layout.activity_main)
        }, 600) // Delay of 300 milliseconds
    }
}
