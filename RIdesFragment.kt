package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_uber.com.example.project_uber.PackageActivity
import com.example.project_uber.databinding.FragmentRidesBinding

class RidesFragment : Fragment() {

    private var _binding: FragmentRidesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener on the button with id `btn_rides_package`
        binding.btnRidesPackage.setOnClickListener {
            // Start the PackageActivity when the button is clicked
            val intent = Intent(requireContext(), PackageActivity::class.java)
            startActivity(intent)
        }
        binding.btnRidesTrip.setOnClickListener {
            // Start the PackageActivity when the button is clicked
            val intent = Intent(requireContext(), TripActivity::class.java)
            startActivity(intent)
        }

        binding.btnRidesReserve.setOnClickListener {
            // Start the PackageActivity when the button is clicked
            val intent = Intent(requireContext(), ReserveActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//class RidesFragment : Fragment() {
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_rides, container, false)
//    }
//}
