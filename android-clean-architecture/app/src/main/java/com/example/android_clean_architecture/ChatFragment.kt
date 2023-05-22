package com.example.android_clean_architecture

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_clean_architecture.adapter.ChatRecyclerAdapter
import com.example.android_clean_architecture.databinding.FragmentChatBinding
import com.example.android_clean_architecture.view_model.ChatViewModel
import com.example.core.FragmentBase

class ChatFragment : FragmentBase<FragmentChatBinding, ChatViewModel>() {
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        token = activity?.getPreferences(Context.MODE_PRIVATE)
            ?.getString(SharedPreferencesKeys.chatToken, "") ?: ""
        if (token.isEmpty()) findNavController().navigate(R.id.action_ChatFragment_to_AuthFragment)
        else {
            viewModel.getMessages(token)

            val recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this.context)
            recyclerView.adapter = ChatRecyclerAdapter(viewModel, this)
        }
    }

    override fun getViewModelClass(): Class<ChatViewModel> {
        return ChatViewModel::class.java
    }

    override fun getViewBinding(): FragmentChatBinding {
        return FragmentChatBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.messageSubmitButton.setOnClickListener {
            val text = binding.messageEditText.text.toString()
            binding.messageEditText.text.clear()
            if(text.isNotEmpty())
                viewModel.sendMessage(token, text)
        }
    }
}