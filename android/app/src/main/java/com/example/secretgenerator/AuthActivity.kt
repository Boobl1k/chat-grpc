package com.example.secretgenerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.secretgenerator.databinding.ActivityAuthBinding


const val KEY = "${R.string.app_package_name}.KEY"

class AuthActivity : AppCompatActivity() {
//    private lateinit var approveTextInput: TextInputEditText
//    private lateinit var errorApproveTextView: TextView

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, LoginFragment::class.java, null)
                .commit()
        }

//        approveTextInput = findViewById(R.id.approveTextInput)
//        errorApproveTextView = findViewById(R.id.errorApproveTextView)
//        val approveButton: Button = findViewById(R.id.approveButton)
//
//        var containsSymbol = false
//        approveTextInput.addTextChangedListener {
//            if (!containsSymbol) {
//                errorApproveTextView.text = ""
//                containsSymbol = true
//            }
//        }
//
//        approveButton.setOnClickListener {
//            if (approveTextInput.text.isNullOrEmpty()) {
//                errorApproveTextView.text = getString(R.string.error_approve_key)
//                containsSymbol = false
//                return@setOnClickListener
//            }
//
//            when (intent.getStringExtra(KEY)) {
//                approveTextInput.text.toString() ->
//                    setResult(RESULT_OK, Intent().putExtra(KEY, generateSecretKey(10)))
//                else -> setResult(RESULT_CANCELED)
//            }
//            finish()
//        }
    }
}