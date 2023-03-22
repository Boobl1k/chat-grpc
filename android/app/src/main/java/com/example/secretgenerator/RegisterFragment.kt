package com.example.secretgenerator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.secretgenerator.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager

    private lateinit var username: String
    private lateinit var password: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PrefManager(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.registerButton.setOnClickListener {
            register()
        }

        binding.alreadyRegisteredButton.setOnClickListener {
            returnToLogin()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun register() {
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
            else -> {
                prefManager.setUsername(username)
                prefManager.setPassword(password)
                returnToLogin()
            }
        }
    }

    private fun returnToLogin() {
        val loginFragment = LoginFragment()
        val transaction: FragmentTransaction =
            activity?.supportFragmentManager!!.beginTransaction()
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        transaction.replace(R.id.fragment_container_view, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}