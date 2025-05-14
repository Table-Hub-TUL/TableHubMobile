package pl.tablehub.mobile.processing.interfaces

import pl.tablehub.mobile.model.websocket.WebSocketMessage

interface IWebsocketMessageProcessor {
    fun start()
    suspend fun process(webSocketMessage: WebSocketMessage)
    fun stop()
}