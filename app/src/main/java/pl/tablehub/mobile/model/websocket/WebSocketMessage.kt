package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketMessage(
    val header: MessageHeader,
    val body: MessageBody
)
