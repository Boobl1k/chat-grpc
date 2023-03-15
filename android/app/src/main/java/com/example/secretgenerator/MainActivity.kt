package com.example.secretgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var keyTextView: TextView

    private fun generatePublicKey() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        keyTextView = findViewById(R.id.keyTextView)
        val generateButton: Button = findViewById(R.id.generateButton)

        generateButton.setOnClickListener {
            keyTextView.text = "amgous))"
        }

    }
}