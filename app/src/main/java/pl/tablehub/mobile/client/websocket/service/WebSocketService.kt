package pl.tablehub.mobile.client.websocket.service

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.websocket.*
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.util.Constants.BACKEND_WS_IP
import pl.tablehub.mobile.util.WSMessageRelay
import javax.inject.Inject
import kotlinx.coroutines.Job
import pl.tablehub.mobile.repository.AuthRepository
import javax.inject.Singleton

@Singleton
class WebSocketService @Inject constructor(
    private val client: WebSocketClient,
    private val messageRelay: WSMessageRelay
) {

    companion object ServiceContract {
        private const val SERVER_URL: String = BACKEND_WS_IP
        private const val DEBUG_TAG: String = "WEB_SOCKET"
        private const val AGGREGATE_TOPIC: String = "/topic/restaurant-aggregates"
        private const val SPECIFIC_TOPIC: String = "/topic/table-updates/{restaurantId}"
    }

    @Inject
    lateinit var serviceScope: CoroutineScope
    private lateinit var stompSession: StompSession
    private var specificSubscriptionJob: Job? = null

    @Inject
    lateinit var authRepository: AuthRepository

    fun connectWebSocket() {
        serviceScope.launch {
            try {
                val token = authRepository.getJWT().first()!!
                val urlWithToken = "$SERVER_URL?token=$token"
                val stompClient = StompClient(client)
                stompSession = stompClient.connect(
                    url = urlWithToken,
                    customStompConnectHeaders = mapOf("Authorization" to "Bearer $token")
                )
                Log.d(DEBUG_TAG, "WebSocket connection established")
                subscribeToAggregateUpdate()
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "WebSocket connection failed", e)
            }
        }
    }

    private fun subscribeToAggregateUpdate() {
        serviceScope.launch {
            stompSession.subscribe(AGGREGATE_TOPIC).collect { frame ->
                val body = frame.bodyAsText
                Log.d(DEBUG_TAG, "Received message: $body")
                messageRelay.emitMessageAggregate(body)
            }
        }
    }

    fun subscribeToSpecificRestaurantUpdate(restaurantId: Long) {
        specificSubscriptionJob?.cancel()

        val topic = SPECIFIC_TOPIC.replace("{restaurantId}", restaurantId.toString())

        specificSubscriptionJob = serviceScope.launch {
            try {
                stompSession.subscribe(topic).collect { frame ->
                    val body = frame.bodyAsText
                    Log.d(DEBUG_TAG, "Received message: $body")
                    messageRelay.emitMessageSpecific(body)
                }
            } catch (e: Exception) {
                Log.d(DEBUG_TAG, "Specific subscription for $topic cancelled or failed", e)
            }
        }
    }

    fun unsubscribeSpecificRestaurantUpdate() {
        specificSubscriptionJob?.cancel()
        specificSubscriptionJob = null
    }

    fun disconnect() {
        serviceScope.launch {
            stompSession.disconnect()
        }
        serviceScope.cancel()
    }
}