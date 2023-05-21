package com.example.android_clean_architecture

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.android_clean_architecture.databinding.FragmentChatBinding
import com.example.android_clean_architecture.view_model.ChatViewModel
import com.example.core.FragmentBase
import kotlin.random.Random

class ChatFragment : FragmentBase<FragmentChatBinding, ChatViewModel>() {
    override fun getViewModelClass(): Class<ChatViewModel> {
        return ChatViewModel::class.java
    }

    override fun getViewBinding(): FragmentChatBinding {
        return FragmentChatBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ChatFragment_to_MyBookFragment)
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