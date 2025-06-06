package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable


@Serializable
data class MessageHeader(
    val messageId: String,
    val correlationId: String?,
    val sender: String,
    val type: MessageType,
    val accessToken: String?,
    val timestamp: Long
)
