package com.example.android_clean_architecture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.android_clean_architecture.databinding.FragmentAuthBinding
import com.example.android_clean_architecture.view_model.AuthViewModel
import com.example.core.FragmentBase

class AuthFragment : FragmentBase<FragmentAuthBinding, AuthViewModel>() {
    override fun getViewModelClass() = AuthViewModel::class.java
    override fun getViewBinding() = FragmentAuthBinding.inflate(layoutInflater)

    override fun observeData() {
        super.observeData()

        //адель сам поправишь понял?
        var success = false
        viewModel.myBookTokenDataMutable.observe(this) {
            if (it == null) {
                Toast.makeText(
                    context,
                    "Wrong username or password",
                    Toast.LENGTH_SHORT
                ).show()
                success = false
            } else {
                val sp = activity?.getPreferences(Context.MODE_PRIVATE)
                sp?.edit()?.putString(SharedPreferencesKeys.myBookToken, it.access_token)?.commit()
                if (success)
                    findNavController().navigate(R.id.action_AuthFragment_to_MyBookFragment)
                else success = true
            }
        }
        viewModel.chatTokenDataMutable.observe(this) {
            if (it == null) {
                Toast.makeText(
                    context,
                    "Wrong username or password",
                    Toast.LENGTH_SHORT
                ).show()
                success = false
            } else {
                val sp = activity?.getPreferences(Context.MODE_PRIVATE)
                sp?.edit()?.putString(SharedPreferencesKeys.chatToken, it.token)?.commit()
                if (success)
                    findNavController().navigate(R.id.action_AuthFragment_to_MyBookFragment)
                else success = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val token = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString(SharedPreferencesKeys.myBookToken, "") ?: ""
        if (token.isNotEmpty())
            findNavController().navigate(R.id.action_AuthFragment_to_MyBookFragment)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text
            val password = binding.passwordEditText.text
            viewModel.login(username.toString(), password.toString())
        }

    }
}