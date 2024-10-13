package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay to display the splash screen for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the SignalActivity after the splash screen
            startActivity(Intent(this, SignalActivity::class.java))
            finish()
        }, 2000)
    }
}
