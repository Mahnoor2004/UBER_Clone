package com.example.project_uber

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uber.databinding.ActivityTripBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class TripActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityTripBinding
    private lateinit var map: GoogleMap
    private lateinit var dbHelper: DBHelper

    private var pickupLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // Initialize the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Set up listeners for the EditText fields
        setupSearchListeners()

        // Set up save trip button
        binding.btnSaveTrip.setOnClickListener {
            saveTripToDatabase()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Default location (Example: New York City)
        val defaultLocation = LatLng(40.7128, -74.0060)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }

    private fun setupSearchListeners() {
        binding.pickupLocation.setOnEditorActionListener { _, _, _ ->
            val pickupAddress = binding.pickupLocation.text.toString()
            val latLng = handleLocationSearch(pickupAddress, "Pickup Point")
            if (latLng != null) pickupLatLng = latLng
            true
        }

        binding.destination.setOnEditorActionListener { _, _, _ ->
            val destinationAddress = binding.destination.text.toString()
            val latLng = handleLocationSearch(destinationAddress, "Destination")
            if (latLng != null) destinationLatLng = latLng
            true
        }
    }

    private fun handleLocationSearch(address: String, title: String): LatLng? {
        if (address.isNotEmpty()) {
            val latLng = getLocationFromAddress(address)
            if (latLng != null) {
                pinLocation(latLng, title)
                return latLng
            } else {
                Toast.makeText(this, "Location not found: $address", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter a valid address", Toast.LENGTH_SHORT).show()
        }
        return null
    }

    private fun getLocationFromAddress(address: String): LatLng? {
        return try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: MutableList<Address>? = geocoder.getFromLocationName(address, 1)
            if (addresses?.isNotEmpty() == true) {
                val location = addresses[0]
                LatLng(location.latitude, location.longitude)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun pinLocation(latLng: LatLng, title: String) {
        map.addMarker(MarkerOptions().position(latLng).title(title))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun saveTripToDatabase() {
        val pickup = pickupLatLng
        val destination = destinationLatLng

        if (pickup == null || destination == null) {
            Toast.makeText(this, "Please set both pickup and destination locations", Toast.LENGTH_SHORT).show()
            return
        }

        val result = dbHelper.insertTrip(
            pickup.toString(),
            destination.toString(),
            System.currentTimeMillis()
        )


        if (result != -1L) {  // Check if the result is not -1 (success)
            Toast.makeText(this, "Trip saved successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to save trip", Toast.LENGTH_SHORT).show()
        }
    }
}

//package com.example.project_uber
//
//import android.location.Address
//import android.location.Geocoder
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.project_uber.databinding.ActivityTripBinding
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import java.util.Locale
//class TripActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var binding: ActivityTripBinding
//    private lateinit var map: GoogleMap
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityTripBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Initialize the map
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        // Set up listeners for the EditText fields
//        setupSearchListeners()
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//        // Default location (Example: New York City)
//        val defaultLocation = LatLng(40.7128, -74.0060)
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
//    }
//
//    private fun setupSearchListeners() {
//        binding.pickupLocation.setOnEditorActionListener { _, _, _ ->
//            val pickupAddress = binding.pickupLocation.text.toString()
//            handleLocationSearch(pickupAddress, "Pickup Location")
//            true
//        }
//
//        binding.destination.setOnEditorActionListener { _, _, _ ->
//            val destinationAddress = binding.destination.text.toString()
//            handleLocationSearch(destinationAddress, "Destination")
//            true
//        }
//    }
//
//    private fun handleLocationSearch(address: String, title: String) {
//        if (address.isNotEmpty()) {
//            val latLng = getLocationFromAddress(address)
//            if (latLng != null) {
//                pinLocation(latLng, title)
//            } else {
//                Toast.makeText(this, "Location not found: $address", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(this, "Please enter a valid address", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun getLocationFromAddress(address: String): LatLng? {
//        return try {
//            val geocoder = Geocoder(this, Locale.getDefault())
//            val addresses: MutableList<Address>? = geocoder.getFromLocationName(address, 1)
//            if (addresses?.isNotEmpty() == true) {
//                val location = addresses[0]
//                LatLng(location.latitude, location.longitude)
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    private fun pinLocation(latLng: LatLng, title: String) {
//        map.addMarker(MarkerOptions().position(latLng).title(title))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//    }
//}
//
//
////package com.example.project_uber
////
////import android.os.Bundle
////import androidx.appcompat.app.AppCompatActivity
////
////class TripActivity : AppCompatActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_trip) // Ensure that you have `activity_trip.xml` in `res/layout`
////    }
////}
