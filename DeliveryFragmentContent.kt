package com.example.project_uber

//import android.app.AlertDialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//
//class DeliveryFragmentContent : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the main content layout for the delivery fragment
//        val view = inflater.inflate(R.layout.fragment_delivery, container, false)
//
//        // Set up the confirm button
//        val confirmButton: TextView = view.findViewById(R.id.deliveryButton)
//        confirmButton.setOnClickListener {
//            showDeliveryDialog()
//        }
//
//        return view
//    }
//
//    private fun showDeliveryDialog() {
//        // Inflate the dialog layout
//        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.button_sheet_delivery_option, null)
//
//        // Build the dialog
//        val dialogBuilder = AlertDialog.Builder(requireContext())
//            .setView(dialogView)
//            .setCancelable(true)
//
//        // Create the dialog
//        val dialog = dialogBuilder.create()
//
//        // Show the dialog
//        dialog.show()
//
//        // Optional: Set up additional buttons or actions within the dialog layout here
//        val deliveryOption: Button = dialogView.findViewById(R.id.deliveryOption) // Use appropriate button IDs
//        deliveryOption.setOnClickListener {
//            // Handle delivery option action here
//            // For example, dismiss the dialog after action
//            dialog.dismiss()
//        }
//
//
//    }
//}




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DeliveryFragmentContent : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the main content layout for the delivery fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }
}

//class DeliveryFragmentContent : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the main content layout for the delivery fragment
//        return inflater.inflate(R.layout.fragment_delivery, container, false)
//    }
//}
