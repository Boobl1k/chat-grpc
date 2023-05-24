package com.example.android_clean_architecture

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_clean_architecture.adapter.StatisticsRecyclerAdapter
import com.example.android_clean_architecture.databinding.FragmentStatisticsBinding
import com.example.android_clean_architecture.view_model.MyBookViewModel
import com.example.core.FragmentBase

class StatisticsFragment : FragmentBase<FragmentStatisticsBinding, MyBookViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString(SharedPreferencesKeys.myBookToken, "") ?: ""
        if (token.isEmpty()) findNavController().navigate(R.id.action_MyBookFragment_to_AuthFragment)
        else {
            viewModel.getBooks(token)

            val recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.adapter = StatisticsRecyclerAdapter(viewModel, this, token, context)
        }
    }

    override fun getViewModelClass(): Class<MyBookViewModel> {
        return MyBookViewModel::class.java
    }

    override fun getViewBinding(): FragmentStatisticsBinding {
        return FragmentStatisticsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.subscribeToStatisticsUpdates()
    }
}