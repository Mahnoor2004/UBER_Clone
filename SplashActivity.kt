package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Make sure you have activity_splash.xml layout for this

        // Use a Handler to delay the splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the MainActivity
            startActivity(Intent(this, SignalActivity::class.java))
            // Finish the current activity
            finish()
        }, 2000) // Delay of 2000 milliseconds (2 seconds)
    }
}
