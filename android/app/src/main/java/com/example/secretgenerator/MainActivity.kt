package com.example.secretgenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

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
            val intent = Intent(this@MainActivity, SecretActivity::class.java)
            intent.putExtra("publicKey", publicKeyTextView.text.toString())
            startActivityForResult(intent, REQUEST_SECRET_KEY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }
}