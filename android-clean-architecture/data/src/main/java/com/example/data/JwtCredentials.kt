package com.example.data

import io.grpc.CallCredentials
import io.grpc.Metadata.ASCII_STRING_MARSHALLER
import io.grpc.Status
import java.util.concurrent.Executor

class JwtCredentials(private val token: String) : CallCredentials() {
    override fun applyRequestMetadata(
        requestInfo: RequestInfo?,
        executor: Executor,
        metadataApplier: MetadataApplier
    ) {
        executor.execute {
            try {
                val headers = io.grpc.Metadata()
                headers.put(
                    io.grpc.Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER),
                    String.format("Bearer %s", token)
                )
                metadataApplier.apply(headers)
            } catch (e: Throwable) {
                metadataApplier.fail(Status.UNAUTHENTICATED.withCause(e))
            }
        }
    }

    override fun thisUsesUnstableApi() {}
}