package com.example.secretgenerator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

fun generateSecretKey(length: Int): String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return List(length) { charset.random() }
        .joinToString("")
}

class MainActivity : AppCompatActivity() {
    private lateinit var keyTextView: TextView
    private lateinit var errorGenerateTextView: TextView

    private companion object {
        const val REQUEST_SECRET_KEY = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        keyTextView = findViewById(R.id.keyTextView)
        errorGenerateTextView = findViewById(R.id.errorGenerateTextView)

        val generateButton: Button = findViewById(R.id.generateButton)
        val moveButton: Button = findViewById(R.id.moveButton)

        generateButton.setOnClickListener {
            keyTextView.text = generateSecretKey(8)
            errorGenerateTextView.text = ""
        }

        moveButton.setOnClickListener {
            if (keyTextView.text.isNotEmpty()) {
                val intent = Intent(this@MainActivity, SecretActivity::class.java)
                intent.putExtra(KEY, keyTextView.text.toString())
                startActivityForResult(intent, REQUEST_SECRET_KEY)
                keyTextView.text = ""
            } else
                errorGenerateTextView.text = getString(R.string.error_generate_key)
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