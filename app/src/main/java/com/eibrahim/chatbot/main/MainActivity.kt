package com.eibrahim.chatbot.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.eibrahim.chatbot.R
import com.eibrahim.chatbot.auth.AuthActivity
import com.eibrahim.chatbot.auth.AuthPreferences
import com.eibrahim.chatbot.auth.api.RetrofitClient
import com.eibrahim.chatbot.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        // Check if user is logged in
        val authPreferences = AuthPreferences(this)
        val token = authPreferences.getToken()
        if (token.isNullOrEmpty()) {
            Log.d("MainActivity", "User is not logged in, navigating to AuthActivity")
//            val isFirstLaunch = authPreferences.isFirstLaunch()
            val startDestination = R.id.nav_login
            val intent = Intent(this, AuthActivity::class.java).apply {
                putExtra("start_destination", startDestination)
            }
            startActivity(intent)
            finish()
            return
        }

        // Proceed with loading UI only if user is logged in
        RetrofitClient.initAuthPreferences(this)
        Log.d("MainActivity", "RetrofitClient initialized")

        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        // Setup Navigation Component
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navView, navController)

        // Setup RecyclerView with Dummy Conversations
        setupRecyclerView()

        // Click Listener - Change Model Button
        binding.navView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.modelBtn).setOnClickListener {
            navController.navigate(R.id.nav_llm_models)
            drawerLayout.close()
        }

        // Click Listener - Profile Image Example
        binding.navView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.profileBtn).setOnClickListener {
            navController.navigate(R.id.nav_settings)
            drawerLayout.close()
        }

        binding.navView.findViewById<android.widget.ImageView>(R.id.hideNav).setOnClickListener {
            drawerLayout.close()
        }
    }

    private fun setupRecyclerView() {
        val dummyData = List(20) { index -> "Chat Message #${index + 1}" }

        val recyclerView = binding.navView.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.re_conversations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ConversationAdapter(dummyData)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }
}
