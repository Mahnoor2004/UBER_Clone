package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uber.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private var verificationId: String? = null

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val SMS_RETRIEVER_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // From google-services.json
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Handle mobile number authentication
//        binding.continueButton.setOnClickListener {
//            val phoneNumber = binding.mobileEditText.text.toString().trim()
//
//            if (phoneNumber.isNotEmpty()) {
//                // Ensure the phone number is in E.164 format
//                val formattedPhoneNumber = if (!phoneNumber.startsWith("+")) {
//                    "+92$phoneNumber" // Prefix with the country code (e.g., Pakistan's code +92)
//                } else {
//                    phoneNumber
//                }
//
//                // Start phone verification
//                startPhoneVerification(formattedPhoneNumber)
//            } else {
//                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
//            }
//        }

        // Handle Google Sign-In
        binding.googleButton.setOnClickListener {
            signInWithGoogle()
        }

        // Start SMS Retriever for OTP auto-detection
//        val client = SmsRetriever.getClient(this)
//        val smsRetrieverTask = client.startSmsRetriever()
//        smsRetrieverTask.addOnSuccessListener {
//            Log.d("SMS Retriever", "SMS Retriever started successfully.")
//        }
//        smsRetrieverTask.addOnFailureListener {
//            Log.e("SMS Retriever", "Failed to start SMS Retriever", it)
//        }
    }

    private fun startPhoneVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)  // Phone number to send the verification code to
            .setTimeout(60L, TimeUnit.SECONDS)  // Timeout for the verification process
            .setActivity(this)  // The current activity
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Handle auto-verification if possible
                    signInWithPhoneCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Handle verification failure
                    Toast.makeText(this@LoginActivity, "Verification failed: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    // Code sent successfully
                    this@LoginActivity.verificationId = verificationId
                    Toast.makeText(this@LoginActivity, "Code sent! Please enter the OTP.", Toast.LENGTH_SHORT).show()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Successfully signed in, proceed to next activity
                    updateUI(firebaseAuth.currentUser)
                } else {
                    // Handle sign-in failure
                    Toast.makeText(this, "Sign-in failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Navigate to MainActivity after successful sign-in
            startActivity(Intent(this, RoleActivity::class.java))
            finish() // Finish current LoginActivity
        } else {
            Toast.makeText(this, "User authentication failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Log.e("LoginActivity", "Google sign-in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(firebaseAuth.currentUser)
                } else {
                    Log.w("LoginActivity", "Google sign-in failed", task.exception)
                }
            }
    }

    // Listen for SMS to auto-detect OTP
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SMS_RETRIEVER_REQUEST_CODE) {
            val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            val otp = extractOtp(message)
            otp?.let {
                verifyOtp(it)
            }
        }
    }

    private fun extractOtp(message: String?): String? {
        // Extract OTP from the SMS message (assuming OTP is a 6-digit number)
        val otpRegex = Regex("\\d{6}")
        return otpRegex.find(message.orEmpty())?.value
    }

    private fun verifyOtp(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneCredential(credential)
    }
}
