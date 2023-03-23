package com.example.secretgenerator

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secretgenerator.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager

    private lateinit var recyclerView: RecyclerView
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefManager = PrefManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        getUsers()

        recyclerView = binding.someRecyclerView
        usersRecyclerAdapter = UsersRecyclerAdapter(ArrayList())

        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = usersRecyclerAdapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUsers() {
        JsonPlaceholderApi.service.makeRequest(
            "https://jsonplaceholder.typicode.com/users/",
            object : RequestCallback {
                override fun onSuccess(response: String) {
                    val userList = Gson().fromJson(response, Array<UserEntity>::class.java)
                        .toList() as ArrayList<UserEntity>

                    Handler(Looper.getMainLooper()).post {
                        usersRecyclerAdapter = UsersRecyclerAdapter(userList)
                        recyclerView.adapter = usersRecyclerAdapter
                    }
                }

                override fun onFailure(error: String) {
                    Log.d("CONNECTION ERROR", "Connection error: $error")
                }
            })
    }

    private fun getUser(id: Int) {
        JsonPlaceholderApi.service.makeRequest(
            "https://jsonplaceholder.typicode.com/users/$id",
            object : RequestCallback {
                override fun onSuccess(response: String) {
                    val list = ArrayList<UserEntity>()
                    val user = Gson().fromJson(response, UserEntity::class.java)
                    list.add(user)

                    Handler(Looper.getMainLooper()).post {
                        usersRecyclerAdapter = UsersRecyclerAdapter(list)
                        recyclerView.adapter = usersRecyclerAdapter
                    }
                }

                override fun onFailure(error: String) {
                    Log.d("CONNECTION ERROR", "Connection error: $error")
                }
            })
    }
}