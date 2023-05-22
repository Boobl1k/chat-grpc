package com.example.domain.use_case

import com.example.domain.dto.ChatMessageData
import com.example.domain.dto.ChatTokenData

interface ChatUseCase {
    suspend fun login(username: String, password: String): ChatTokenData?
    suspend fun sendMessage(token: String, text: String)
    suspend fun getMessages(token: String, onNewMessage: (message: ChatMessageData) -> Unit)
}