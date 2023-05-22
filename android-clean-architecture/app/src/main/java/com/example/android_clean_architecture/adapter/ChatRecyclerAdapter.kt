package com.example.android_clean_architecture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.android_clean_architecture.R
import com.example.android_clean_architecture.view_model.ChatViewModel

class ChatRecyclerAdapter(
    private val viewModel: ChatViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<MessageViewHolder>() {

    private var itemCount = 1

    init {
        setHasStableIds(false)
        viewModel.messagesDataMutable.observe(lifecycleOwner) {
            if(it.isNotEmpty())
                itemCount = it.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder =
        MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_message_view, parent, false)
        )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        viewModel.messagesDataMutable.observe(lifecycleOwner) {
            if(it.size <= position)
            {
                holder.senderNameView.text = ""
                holder.messageTextView.text = ""
            } else {
                holder.senderNameView.text = it[position].authorName
                holder.messageTextView.text = it[position].text
            }
        }
    }

    override fun getItemCount() = itemCount
}