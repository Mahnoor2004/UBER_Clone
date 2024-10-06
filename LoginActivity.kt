package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uber.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set click listener for "Continue with Google" button
        binding.googleButton.setOnClickListener {
            signInWithGoogle()
        }

        // Other buttons click listeners
        binding.continueButton.setOnClickListener {
            // Perform the action on "Continue" button click
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.emailButton.setOnClickListener {
            // Perform the action on "Continue with Email" button click
        }

        binding.findAccountButton.setOnClickListener {
            // Perform the action on "Find my account" button click
        }
    }

    // Function to start the Google Sign-In process
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // Handle the result of the Google Sign-In process
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            handleSignInResult(account)
        } catch (e: ApiException) {
            Log.w("LoginActivity", "Google sign-in failed", e)
        }
    }

    // Function to handle the result of Google Sign-In
    private fun handleSignInResult(account: GoogleSignInAccount?) {
        if (account != null) {
            // Sign-in was successful, navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish LoginActivity so user can't go back
        }
    }
}
