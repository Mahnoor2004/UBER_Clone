package com.example.project_uber

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.project_uber.R

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Find the TextView for Family and set a click listener
        val familyTextView = view.findViewById<TextView>(R.id.familyTextView)
        familyTextView.setOnClickListener {
            // Start FamilyActivity when clicked
            val intent = Intent(activity, FamilyActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
