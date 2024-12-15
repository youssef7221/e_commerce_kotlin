package com.example.e_commerce_kot

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.e_commerce_kot.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class Profile : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize Firebase Auth
        val auth = FirebaseAuth.getInstance()

        // Find buttons in the layout
        val changeThemeButton = view.findViewById<Button>(R.id.changeThemeButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)
        sharedPreferences = requireContext().getSharedPreferences("ThemePref", Context.MODE_PRIVATE)
        // Set up Change Theme button click listener
        changeThemeButton.setOnClickListener {
            toggleTheme()
            Toast.makeText(requireContext(), "Change Theme clicked!", Toast.LENGTH_SHORT).show()
        }

        // Set up Log Out button click listener
        logoutButton.setOnClickListener {
            auth.signOut() // Sign out from Firebase
            Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return view
    }
    private fun toggleTheme() {
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        if (isDarkMode) {
            // Switch to Light Theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            saveThemePreference(false)
        } else {
            // Switch to Dark Theme
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            saveThemePreference(true)
        }
    }

    private fun saveThemePreference(isDarkMode: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean("isDarkMode", isDarkMode)
            apply()
        }
    }
}
