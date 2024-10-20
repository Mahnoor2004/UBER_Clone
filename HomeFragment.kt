package com.example.project_uber

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.project_uber.databinding.FragmentHomeBinding // Import the generated ViewBinding class
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        // Link the TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_custom_layout, null)
            val tabIcon = tabView.findViewById<ImageView>(R.id.tab_icon)
            val tabText = tabView.findViewById<TextView>(R.id.tab_text)

            when (position) {
                0 -> {
                    tabText.text = "Rides"
                    tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f) // Adjust the size as needed
                    tabText.setTypeface(null, Typeface.BOLD) // Make the text bold
                    tabIcon.setImageResource(R.drawable.car1) // Set the car icon
                }
                1 -> {
                    tabText.text = "Delivery"
                    tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f) // Adjust the size as needed
                    tabText.setTypeface(null, Typeface.BOLD) // Make the text bold
                    tabIcon.setImageResource(R.drawable.ic_food) // Set the delivery icon
                }
            }


            tab.customView = tabView
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // To prevent memory leaks
    }

    // Adapter class for ViewPager2
    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return 2 // We have 2 tabs: Rides and Delivery
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RidesFragment()
                else -> DeliveryFragment()
            }
        }
    }
}
