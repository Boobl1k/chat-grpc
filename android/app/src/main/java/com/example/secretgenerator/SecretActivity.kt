package com.example.secretgenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText

const val KEY = "${R.string.app_package_name}.KEY"

class SecretActivity : AppCompatActivity() {
    private lateinit var approveTextInput: TextInputEditText
    private lateinit var errorApproveTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        approveTextInput = findViewById(R.id.approveTextInput)
        errorApproveTextView = findViewById(R.id.errorApproveTextView)
        val approveButton: Button = findViewById(R.id.approveButton)

        approveTextInput.addTextChangedListener {
            errorApproveTextView.text = ""
        }

        approveButton.setOnClickListener {
            if (approveTextInput.text.isNullOrEmpty()) {
                errorApproveTextView.text = getString(R.string.error_approve_key)
                return@setOnClickListener
            }

            when (intent.getStringExtra(KEY)) {
                approveTextInput.text.toString() ->
                    setResult(RESULT_OK, Intent().putExtra(KEY, generateSecretKey(10)))
                else -> setResult(RESULT_CANCELED)
            }
            finish()
        }
    }
}