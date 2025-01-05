package com.example.project_uber

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uber.databinding.ActivityRoleBinding
class RoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this) // Correctly initializing DBHelper with Context

        // Set up Register Button Click Listener
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            val role = binding.spinnerRole.selectedItem.toString()

            // Validate input
            if (name.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert data into the database
            val isInserted = dbHelper.insertUser(name, phoneNumber, role)
            if (isInserted) {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                binding.etName.text.clear()
                binding.etPhoneNumber.text.clear()

                // Get the user role (this should be done correctly)
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val userRole = sharedPreferences.getString("user_role", "passenger") ?: "passenger"

                if (userRole == "driver") {
                    // Navigate to the Driver's details page
                    val intent = Intent(this, DriverVehicleInfoActivity::class.java)
                    startActivity(intent)
                } else {
                    // Assuming userId is available (retrieve it from DB or SharedPreferences)
                    val userId = getUserId() // Implement this function to fetch user ID
                    val riderId = generateRiderId() // Generate or retrieve rider ID

                    // Insert rider information into the database
                    dbHelper.insertRider(userId, riderId)

                    // Navigate to the HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Function to get the user ID from SharedPreferences (or database)
    private fun getUserId(): Int {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("user_id", -1) // Default value is -1 if not found
    }

    // Function to generate a rider ID (if needed)
    private fun generateRiderId(): Int {
        // You can generate or retrieve the rider ID here
        return (Math.random() * 1000).toInt() // Example of generating a random ID
    }
}
