package com.example.secretgenerator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.secretgenerator.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PrefManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.helloTextView.text = "Hello, ${prefManager.getUsername()}"

        binding.curUsernameTextInput.setText(prefManager.getUsername())
        binding.curPasswordTextInput.setText(prefManager.getPassword())

        binding.deleteButton.setOnClickListener {
            deleteAccount()
        }

        binding.logoutButton.setOnClickListener {
            logout()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteAccount() {
        prefManager.removeData()
        openAuth()
    }

    private fun logout() {
        prefManager.setAuth(false)
        openAuth()
    }

    private fun openAuth() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}