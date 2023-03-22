package com.example.secretgenerator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import com.example.secretgenerator.databinding.FragmentFetchBinding
import okhttp3.*
import java.io.IOException


class FetchFragment : Fragment() {
    private var _binding: FragmentFetchBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager
    private val client = OkHttpClient()

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
        _binding = FragmentFetchBinding.inflate(inflater, container, false)


        binding.logoutButton.setOnClickListener {
            logout()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitAll().build())

        binding.urlButton.setOnClickListener {
            Thread {
                runAsync()
            }.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @WorkerThread
    private fun runAsync() {
        var url = "https://jsonplaceholder.typicode.com/todos/1"
        if (url.isNotEmpty()) {
            val fetch = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .build()

            fetch.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.i("Response", "Responsed receive from server")
                    response.use {
                        if (!response.isSuccessful)
                            Log.e("HTTP Error", "Smth didn't load")
                        else {
                            val body = response?.body?.string()

                            Handler(Looper.getMainLooper()).post {
                                binding.justTextView.text = body
                            }

//                            Log.i("RESPONSE", "$body")
                        }
                    }
                }
            })

        } else {
            binding.justTextView.text = "URL was Empty"
        }
    }

    private fun logout() {
        prefManager.setAuth(false)

        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}