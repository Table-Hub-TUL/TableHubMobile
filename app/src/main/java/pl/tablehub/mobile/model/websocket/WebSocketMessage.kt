package pl.tablehub.mobile.model.websocket

data class WebSocketMessage(
    val header: MessageHeader,
    val body: MessageBody
)
