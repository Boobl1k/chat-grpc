package com.example.android_clean_architecture

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_clean_architecture.adapter.CustomRecyclerAdapter
import com.example.android_clean_architecture.databinding.FragmentSecondBinding
import com.example.android_clean_architecture.view_model.SecondViewModel
import com.example.core.FragmentBase

class SecondFragment : FragmentBase<FragmentSecondBinding, SecondViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = CustomRecyclerAdapter(viewModel, this)
    }

    override fun getViewModelClass(): Class<SecondViewModel> {
        return SecondViewModel::class.java
    }

    override fun getViewBinding(): FragmentSecondBinding {
        return FragmentSecondBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}