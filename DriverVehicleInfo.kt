package com.example.project_uber


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DriverVehicleInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_vehicle_info)

        // Get references to the input fields and save button
        val vehicleModelEditText = findViewById<EditText>(R.id.vehicleModelEditText)
        val vehicleRegistrationEditText = findViewById<EditText>(R.id.vehicleRegistrationEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Handle save button click
        saveButton.setOnClickListener {
            val vehicleModel = vehicleModelEditText.text.toString()
            val vehicleRegistration = vehicleRegistrationEditText.text.toString()

            if (vehicleModel.isNotEmpty() && vehicleRegistration.isNotEmpty()) {
                // Save vehicle info in the database
                val driverId = getDriverId() // You can get the driver ID from shared preferences or database
                saveVehicleInfo(driverId, vehicleModel, vehicleRegistration)

                Toast.makeText(this, "Vehicle information saved successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish() // Close activity after saving
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveVehicleInfo(driverId: Int, vehicleModel: String, vehicleRegistration: String) {
        val dbHelper = DBHelper(this) // Create an instance of DBHelper
        val db = dbHelper.writableDatabase // Get writable database
        val contentValues = ContentValues().apply {
            put(dbHelper.getColumnDriverId(), driverId) // Use the getter method for DRIVER_ID
            put(dbHelper.getColumnVehicleModel(), vehicleModel) // Use the getter method for VEHICLE_MODEL
            put(dbHelper.getColumnVehicleRegistration(), vehicleRegistration) // Use the getter method for VEHICLE_REGISTRATION
        }
        db.insert(dbHelper.getTableVehicles(), null, contentValues) // Use the getter method for VEHICLES table
    }


    // Function to get the driver's ID, assuming it’s stored in SharedPreferences
    private fun getDriverId(): Int {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("driver_id", -1) // Example of getting driver ID
    }

}
