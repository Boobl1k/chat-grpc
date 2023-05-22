package com.example.android_clean_architecture.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_clean_architecture.R

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val senderNameView: TextView
    val messageTextView: TextView

    init {
        senderNameView = view.findViewById(R.id.authorName)
        messageTextView = view.findViewById(R.id.messageText)
    }

}