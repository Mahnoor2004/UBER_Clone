package com.example.project_uber

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "RideApp.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE_NUMBER = "phone_number"
        private const val COLUMN_ROLE = "role"

        private const val TABLE_VEHICLES = "vehicles"
        private const val COLUMN_VEHICLE_ID = "vehicle_id"
        private const val COLUMN_DRIVER_ID = "driver_id"
        private const val COLUMN_VEHICLE_MODEL = "vehicle_model"
        private const val COLUMN_VEHICLE_REGISTRATION = "vehicle_registration"



        // Rider Table
        private const val TABLE_RIDERS = "riders"
        private const val COLUMN_RIDER_ID = "rider_id"
        private const val COLUMN_USER_REFERENCE_ID = "user_id"

        //trip Table

        private const val TABLE_TRIPS = "trips"
        private const val COLUMN_TRIP_ID = "trip_id"
        private const val COLUMN_PICKUP = "pickup"
        private const val COLUMN_DESTINATION = "destination"
        private const val COLUMN_TIMESTAMP = "timestamp"


    }
    // Getter methods
    fun getTableVehicles() = TABLE_VEHICLES
    fun getColumnDriverId() = COLUMN_DRIVER_ID
    fun getColumnVehicleModel() = COLUMN_VEHICLE_MODEL
    fun getColumnVehicleRegistration() = COLUMN_VEHICLE_REGISTRATION
    override fun onCreate(db: SQLiteDatabase?) {
        // Create Users Table
        val CREATE_USERS_TABLE = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_PHONE_NUMBER TEXT UNIQUE, " +
                "$COLUMN_ROLE TEXT);"
        db?.execSQL(CREATE_USERS_TABLE)

        // Create Vehicles Table
        val CREATE_VEHICLES_TABLE = "CREATE TABLE $TABLE_VEHICLES (" +
                "$COLUMN_VEHICLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_DRIVER_ID INTEGER, " +
                "$COLUMN_VEHICLE_MODEL TEXT, " +
                "$COLUMN_VEHICLE_REGISTRATION TEXT, " +
                "FOREIGN KEY($COLUMN_DRIVER_ID) REFERENCES $TABLE_USERS($COLUMN_USER_ID));"
        db?.execSQL(CREATE_VEHICLES_TABLE)

        // Create Riders Table
        val CREATE_RIDERS_TABLE = "CREATE TABLE $TABLE_RIDERS (" +
                "$COLUMN_RIDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USER_REFERENCE_ID INTEGER, " +
                "FOREIGN KEY($COLUMN_USER_REFERENCE_ID) REFERENCES $TABLE_USERS($COLUMN_USER_ID));"
        db?.execSQL(CREATE_RIDERS_TABLE)

        //Create Trips Table
// Create Trips Table
        val CREATE_TRIPS_TABLE = "CREATE TABLE $TABLE_TRIPS (" +
                "$COLUMN_TRIP_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_PICKUP TEXT," +
                "$COLUMN_DESTINATION TEXT," +
                "$COLUMN_TIMESTAMP INTEGER)"
        db?.execSQL(CREATE_TRIPS_TABLE)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_VEHICLES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_RIDERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(name: String, phoneNumber: String, role: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE_NUMBER, phoneNumber)
            put(COLUMN_ROLE, role)
        }

        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun insertVehicle(driverId: Int, vehicleModel: String, vehicleRegistration: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DRIVER_ID, driverId)
            put(COLUMN_VEHICLE_MODEL, vehicleModel)
            put(COLUMN_VEHICLE_REGISTRATION, vehicleRegistration)
        }

        val result = db.insert(TABLE_VEHICLES, null, values)
        db.close()
        return result != -1L
    }

    // Function to insert user and then rider data
    fun insertRider(userId: Int, riderId: Int) {
        val db = writableDatabase // Use writableDatabase for DB operations
        val riderContentValues = ContentValues().apply {
            put(COLUMN_USER_REFERENCE_ID, userId) // Reference to the user's ID
            put(COLUMN_RIDER_ID, riderId)
        }

        db.insert(TABLE_RIDERS, null, riderContentValues) // Insert into the 'riders' table
    }

    fun insertTrip(pickup: String, destination: String, timestamp: Long): Long {
        val db = this.writableDatabase // Get a writable database instance

        // Create a content values object to hold the trip data
        val contentValues = ContentValues().apply {
            put(COLUMN_PICKUP, pickup)          // Put pickup location into content values
            put(COLUMN_DESTINATION, destination) // Put destination location into content values
            put(COLUMN_TIMESTAMP, timestamp)    // Put timestamp into content values
        }

        // Insert the data into the table and return the trip ID (autoincremented)
        return db.insert(TABLE_TRIPS, null, contentValues)
    }

}