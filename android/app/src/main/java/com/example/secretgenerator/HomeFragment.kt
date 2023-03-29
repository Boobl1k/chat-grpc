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

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter

    private lateinit var todosRecyclerView: RecyclerView
    private lateinit var todosRecyclerAdapter: TodosRecyclerAdapter

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
        getTodos(0, 20)

        initUsersRecycler()
        initTodosRecycler()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUsersRecycler() {
        usersRecyclerView = binding.usersRecyclerView
        usersRecyclerAdapter = UsersRecyclerAdapter(ArrayList())

        usersRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        usersRecyclerView.adapter = usersRecyclerAdapter
    }

    private fun initTodosRecycler() {
        todosRecyclerView = binding.todosRecyclerView
        todosRecyclerAdapter = TodosRecyclerAdapter(ArrayList())

        todosRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        todosRecyclerView.adapter = todosRecyclerAdapter
    }

    private fun getTodos(start: Int, end: Int) {
        JsonPlaceholderApi.service.makeRequest(
            "https://jsonplaceholder.typicode.com/todos?_start=${start}&_end=${end}",
            object : RequestCallback {
                override fun onSuccess(response: String) {
                    val todoList = Gson().fromJson(response, Array<TodoEntity>::class.java)
                        .toList() as ArrayList<TodoEntity>

                    Handler(Looper.getMainLooper()).post {
                        todosRecyclerAdapter = TodosRecyclerAdapter(todoList)
                        todosRecyclerView.adapter = todosRecyclerAdapter
                    }
                }

                override fun onFailure(error: String) {
                    Log.d("CONNECTION ERROR", "Connection error: $error")
                }
            })
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
                        usersRecyclerView.adapter = usersRecyclerAdapter
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
                        usersRecyclerView.adapter = usersRecyclerAdapter
                    }
                }

                override fun onFailure(error: String) {
                    Log.d("CONNECTION ERROR", "Connection error: $error")
                }
            })
    }
}