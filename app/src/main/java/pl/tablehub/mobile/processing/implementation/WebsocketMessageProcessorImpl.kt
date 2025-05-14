package pl.tablehub.mobile.processing.implementation

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.tablehub.mobile.model.websocket.WebSocketMessage
import pl.tablehub.mobile.processing.interfaces.IWebsocketMessageProcessor
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.util.WSMessageRelay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebsocketMessageProcessorImpl @Inject constructor(
    private val messageRelay: WSMessageRelay,
    private val restaurantsRepository: IRestaurantsRepository
) : IWebsocketMessageProcessor {
    private val processorScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        start()
    }

    override fun start() {
        messageRelay.messageFlow.onEach { message ->
            try {
                process(message)
            } catch (e : Exception) {
                Log.e("WebsocketMessageProcessor", "Error processing message", e)
            }
        }.launchIn(processorScope)
    }

    override suspend fun process(webSocketMessage: WebSocketMessage) {
        TODO("Not yet implemented")
    }

    override fun stop() {
        processorScope.cancel()
    }
}