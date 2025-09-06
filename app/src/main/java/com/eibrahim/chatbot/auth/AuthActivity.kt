package com.eibrahim.chatbot.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.eibrahim.chatbot.R
class AuthActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Initialize NavController
        navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_auth)?.findNavController()
        if (navController == null) {
            Log.e("AuthActivity", "NavController failed to initialize")
            return
        }
        Log.d("AuthActivity", "NavController initialized successfully")

        // Get start destination from Intent or default to loginFragment
        val startDestination = intent.getIntExtra("start_destination", R.id.nav_login)
        Log.d("AuthActivity", "Received start destination: $startDestination")

        // Set navigation graph with start destination
        try {
            navController?.setGraph(R.navigation.auth_navigation, Bundle().apply {
                putInt("start_destination_id", startDestination)
            })
            Log.d("AuthActivity", "Navigation graph set with start destination: $startDestination")
        } catch (e: Exception) {
            Log.e("AuthActivity", "Failed to set navigation graph: ${e.message}", e)
        }

    }
}