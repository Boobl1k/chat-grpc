package com.example.android_clean_architecture

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.android_clean_architecture.databinding.FragmentFirstBinding
import com.example.android_clean_architecture.view_model.FirstViewModel
import com.example.core.FragmentBase
import kotlin.random.Random

class FirstFragment : FragmentBase<FragmentFirstBinding, FirstViewModel>() {
    override fun getViewModelClass(): Class<FirstViewModel> {
        return FirstViewModel::class.java
    }

    override fun getViewBinding(): FragmentFirstBinding {
        return FragmentFirstBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.todoDataMutable.observe(this) {
            if (it == null) binding.resultTextView.text = "Данные пусты"
            else binding.resultTextView.text = it.toString()
        }
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.loadButtonFirst.setOnClickListener {
            viewModel.getTodoData(Random.nextInt(50))
        }
    }
}