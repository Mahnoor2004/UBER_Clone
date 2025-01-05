package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_uber.com.example.project_uber.PackageActivity
import com.example.project_uber.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set onClickListeners for buttons
        binding.btnTrip.setOnClickListener {
            startActivity(Intent(requireContext(), TripActivity::class.java))
        }

//        binding.btnFood.setOnClickListener {
//            startActivity(Intent(requireContext(), DeliveryFragment::class.java))
//        }
        binding.btnFood.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DeliveryFragment())
                .addToBackStack(null) // Optional: Allows back navigation
                .commit()
        }

        binding.btnReserve.setOnClickListener {
            startActivity(Intent(requireContext(), ReserveActivity::class.java))
        }

        binding.btnPackage.setOnClickListener {
            startActivity(Intent(requireContext(), PackageActivity::class.java))
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//package com.example.project_uber
//
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.project_uber.R
//
//class ServicesFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_services, container, false)
//    }
//}
