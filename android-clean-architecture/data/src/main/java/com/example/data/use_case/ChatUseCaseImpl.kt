package com.example.data.use_case

import com.example.android_clean_architecture.*
import com.example.data.JwtCredentials
import com.example.domain.dto.ChatMessageData
import com.example.domain.dto.ChatTokenData
import com.example.domain.use_case.ChatUseCase
import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.wait

class ChatUseCaseImpl : ChatUseCase {
    private val channel: ManagedChannel =
        ManagedChannelBuilder.forAddress("10.0.2.2", 5000).usePlaintext().build()

    override suspend fun login(username: String, password: String) = try {
        val res = AuthGrpc.newBlockingStub(channel)
            .login(
                UserCredentials.newBuilder().setUsername(username).setPassword(password).build()
            )
        ChatTokenData(res.token)
    } catch (_: java.lang.Exception) {
        null
    }

    override suspend fun sendMessage(token: String, text: String) {
        ChatGrpc.newBlockingStub(channel).withCallCredentials(JwtCredentials(token))
            .sendMessage(SendMessageRequest.newBuilder().setText(text).build())
    }

    override suspend fun getMessages(
        token: String,
        onNewMessage: (message: ChatMessageData) -> Unit
    ) {
        ChatGrpcKt.ChatCoroutineStub(channel).withCallCredentials(JwtCredentials(token))
            .getMessages(Empty.getDefaultInstance()).collect {
                println(it.text)
                onNewMessage(ChatMessageData(it.id, it.text, it.authorUsername))
            }
    }
}
