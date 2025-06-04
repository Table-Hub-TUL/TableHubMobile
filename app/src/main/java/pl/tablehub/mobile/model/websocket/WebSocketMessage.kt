package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WebSocketMessage(
    val header: MessageHeader,
    val body: JsonElement
)
