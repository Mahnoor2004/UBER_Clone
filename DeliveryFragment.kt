package com.example.project_uber

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class DeliveryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val splashView = inflater.inflate(R.layout.fragment_delivery_splash, container, false)

        // Delay for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, DeliveryFragmentContent())
                addToBackStack(null)
                commit()
            }
        }, 3000)

        return splashView
    }
}

//package com.example.project_uber
//
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.project_uber.R
//
//class DeliveryFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the splash screen layout
//        val splashView = inflater.inflate(R.layout.fragment_delivery_splash, container, false)
//
//        // Use a Handler to delay showing the actual fragment layout
//        Handler(Looper.getMainLooper()).postDelayed({
//            // Use FragmentTransaction to replace the splash screen with the main layout
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.fragment_container, DeliveryFragmentContent())
//                commit()
//            }
//        }, 3000) // 3000 milliseconds = 3 seconds
//
//        return splashView
//    }
//}
