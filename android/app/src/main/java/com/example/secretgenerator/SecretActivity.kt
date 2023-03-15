package com.example.secretgenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

const val KEY = "${R.string.app_package_name}.KEY"

class SecretActivity : AppCompatActivity() {
    private lateinit var checkTextInput: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        checkTextInput = findViewById(R.id.checkTextInput)
        val checkButton: Button = findViewById(R.id.checkButton)

        val publicKey = intent.getStringExtra(KEY)

        checkButton.setOnClickListener {
            val answerIntent = Intent()

            if (checkTextInput.text.isNullOrEmpty())
                return@setOnClickListener

            if (checkTextInput.text.toString() == publicKey) {
                answerIntent.putExtra(KEY, "XDDDD")
                setResult(RESULT_OK, answerIntent)
            } else
                setResult(RESULT_CANCELED, answerIntent)

            finish()
        }

    }

}