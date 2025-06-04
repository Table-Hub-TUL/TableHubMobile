package pl.tablehub.mobile.processing.implementation

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import pl.tablehub.mobile.model.websocket.MessageType
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
import pl.tablehub.mobile.model.websocket.TableStatusChange
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
        when (webSocketMessage.header.type) {
            MessageType.QUERY_RESTAURANTS_RESPONSE -> processRestaurantsResponse(webSocketMessage)
            MessageType.SUBSCRIBE_RESTAURANT_UPDATES_RESPONSE -> processSubscriptionResponse(webSocketMessage)
            MessageType.TABLE_STATUS_CHANGED_EVENT -> processTableStatusChangedEvent(webSocketMessage)
            MessageType.UPDATE_TABLE_STATUS_RESPONSE -> {}
            MessageType.ERROR_RESPONSE -> {}
            else -> {}
        }
    }

    private suspend fun processRestaurantsResponse(webSocketMessage: WebSocketMessage) {
        val bodyJson = Json.encodeToJsonElement(webSocketMessage.body)
        val restaurantsResponse = Json.decodeFromJsonElement<RestaurantsResponse>(bodyJson)
        restaurantsRepository.processRestaurantList(restaurantsResponse.restaurants)
    }

    private suspend fun processSubscriptionResponse(webSocketMessage: WebSocketMessage) {
        val bodyJson = Json.encodeToJsonElement(webSocketMessage.body)
        val response = runCatching {
            Json.decodeFromJsonElement<RestaurantSubscriptionResponse>(bodyJson)
        }.getOrNull()

        response?.takeIf { it.success }?.let {
            restaurantsRepository.processInitialRestaurantData(it.initialState.id, it.initialState)
        }
    }

    private suspend fun processTableStatusChangedEvent(webSocketMessage: WebSocketMessage) {
        val bodyJson = Json.encodeToJsonElement(webSocketMessage.body)
        val tableStatusChangedEvent = Json.decodeFromJsonElement<TableStatusChange>(bodyJson)
        restaurantsRepository.processTableStatusChange(tableStatusChangedEvent)
    }

    override fun stop() {
        processorScope.cancel()
    }
}