package com.example.android_clean_architecture

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_clean_architecture.adapter.BooksRecyclerAdapter
import com.example.android_clean_architecture.databinding.FragmentMyBookBinding
import com.example.android_clean_architecture.view_model.MyBookViewModel
import com.example.core.FragmentBase

class MyBookFragment : FragmentBase<FragmentMyBookBinding, MyBookViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString(SharedPreferencesKeys.myBookToken, "") ?: ""
        if (token.isEmpty()) findNavController().navigate(R.id.action_MyBookFragment_to_AuthFragment)
        else {
            viewModel.getBooks(token)

            val recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.adapter = BooksRecyclerAdapter(viewModel, this)
        }
    }

    override fun getViewModelClass(): Class<MyBookViewModel> {
        return MyBookViewModel::class.java
    }

    override fun getViewBinding(): FragmentMyBookBinding {
        return FragmentMyBookBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogout.setOnClickListener {
            activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
                ?.putString(SharedPreferencesKeys.myBookToken, null)?.commit()
            findNavController().navigate(R.id.action_MyBookFragment_to_AuthFragment)
        }
    }
}