package com.example.secretgenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secretgenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)

        checkLoggedIn()

        binding.justTextView.text = "Welcome, ${prefManager.getUsername()}"

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun checkLoggedIn() {
        if (prefManager.isLoggedIn() == false) {
            openAuth()
        }
    }

    private fun logout() {
        prefManager.setAuth(false)
        openAuth()
    }

    private fun openAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}