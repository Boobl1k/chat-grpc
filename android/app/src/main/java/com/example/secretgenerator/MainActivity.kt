package com.example.secretgenerator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var publicKeyTextView: TextView

    private fun generatePublicKey() {

    }

    private companion object {
        const val REQUEST_SECRET_KEY = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        publicKeyTextView = findViewById(R.id.keyTextView)
        val generateButton: Button = findViewById(R.id.generateButton)
        val moveButton: Button = findViewById(R.id.moveButton)

        generateButton.setOnClickListener {
            publicKeyTextView.text = "amgous))"
        }

        moveButton.setOnClickListener {
            if (publicKeyTextView.text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, SecretActivity::class.java)
                intent.putExtra(KEY, publicKeyTextView.text.toString())
                startActivityForResult(intent, REQUEST_SECRET_KEY)
                publicKeyTextView.text = ""
            } else
                Toast.makeText(this@MainActivity, "YOU SHOULD GENERATE KEY", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_SECRET_KEY -> {
                    val key = data?.getStringExtra(KEY)
                    Toast.makeText(this@MainActivity, key, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(
                this@MainActivity,
                "ERROR: WRONG KEY PROVIDED",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}