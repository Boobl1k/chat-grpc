package com.example.secretgenerator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.secretgenerator.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager

    private lateinit var username: String
    private lateinit var password: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PrefManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.notRegisteredButton.setOnClickListener {
            openRegistration()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login() {
        username = binding.usernameTextInput.text.toString()
        password = binding.passwordTextInput.text.toString()

        when {
            username.isEmpty() -> {
                binding.usernameTextInput.error = "Empty username"
                binding.usernameTextInput.requestFocus()
            }
            password.isEmpty() -> {
                binding.passwordTextInput.error = "Empty password"
                binding.passwordTextInput.requestFocus()
            }
            username != prefManager.getUsername() || password != prefManager.getPassword() -> {
                Toast.makeText(activity, "Wrong Credentials!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                prefManager.setAuth(true)

                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun openRegistration() {
        val registerFragment = RegisterFragment()
        val transaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.fragment_container_view, registerFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}