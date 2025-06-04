package pl.tablehub.mobile.services.websocket

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.stomp.config.HeartBeat
import org.hildan.krossbow.stomp.config.StompConfig
import org.hildan.krossbow.stomp.headers.StompHeaders
import org.hildan.krossbow.websocket.*
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.WebSocketMessage
import pl.tablehub.mobile.util.Constants.BACKEND_IP
import pl.tablehub.mobile.util.WSMessageRelay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log
import kotlin.time.Duration.Companion.seconds

@Singleton
class WebSocketService @Inject constructor(
    private val client: WebSocketClient,
    private val messageRelay: WSMessageRelay
) {

    companion object ServiceContract {
        private const val SERVER_URL: String = "ws://${BACKEND_IP}/ws"
        private const val DEBUG_TAG: String = "WEB_SOCKET"
        private const val DEST_SEND_UPDATE_TABLE = "/app/updateTableStatus"
        private const val DEST_INITIAL_STATUS = "/app/initialStatus"
        //private const val DEST_SUBSCRIBE_INITIAL_STATUS = "/user/queue/initialStatus"
        private const val DEST_SUBSCRIBE_INITIAL_STATUS = "/topic/restaurant/status"
        private const val DEST_SUBSCRIBE_UPDATE_TABLE = "/topic/restaurant/updates"
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var stompSession: StompSession

    @Inject
    lateinit var dataStore: EncryptedDataStore

    fun connectWebSocket() {
        serviceScope.launch {
            try {
                val token = dataStore.getJWT().first()!!
                val urlWithToken = "$SERVER_URL?token=$token"
                val stompClient = StompClient(client)
                Log.d("WEB_SOCKET", "CONNECTED")
                stompSession = stompClient.connect(url = urlWithToken)
                requestInitialStatus()
                subscribeToInitialStatus()
                //subscribeToUpdateTableStatus()
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "WebSocket connection failed", e)
            }
        }
    }

    private fun requestInitialStatus() {
        serviceScope.launch {
            try {
                Log.d(DEBUG_TAG, "Requesting initial status")
                stompSession.sendText(
                    destination = DEST_INITIAL_STATUS,
                    body = ""
                )
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Failed to request initial status", e)
            }
        }
    }

    private fun subscribeToInitialStatus() {
        serviceScope.launch {
            try {
                Log.d(DEBUG_TAG, "Subscribing to $DEST_SUBSCRIBE_INITIAL_STATUS")
                stompSession.subscribe(DEST_SUBSCRIBE_INITIAL_STATUS).collect { frame ->
                    val body = frame.bodyAsText
                    Log.d(DEBUG_TAG, "Received initial status: $body")
                    try {
                        val subscriptionResponse = Json.decodeFromString<WebSocketMessage>(body)
                        messageRelay.emitMessage(subscriptionResponse)
                    } catch (e: Exception) {
                        Log.e(DEBUG_TAG, "Failed to parse initial status message", e)
                    }
                }
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Initial status subscription failed", e)
            }
        }
    }

    private fun subscribeToUpdateTableStatus() {
        serviceScope.launch {
            try {
                Log.d(DEBUG_TAG, "Subscribing to $DEST_SUBSCRIBE_UPDATE_TABLE")
                stompSession.subscribe(DEST_SUBSCRIBE_UPDATE_TABLE).collect { frame ->
                    val body = frame.bodyAsText
                    Log.d(DEBUG_TAG, "Received table update: $body")
                    try {
                        val updateResponse = Json.decodeFromString<WebSocketMessage>(body)
                        messageRelay.emitMessage(updateResponse)
                    } catch (e: Exception) {
                        Log.e(DEBUG_TAG, "Failed to parse table update message", e)
                    }
                }
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Table update subscription failed", e)
            }
        }
    }

    fun sendStatusUpdate(tableUpdateRequest: TableUpdateRequest) {
        serviceScope.launch {
            try {
                stompSession.sendText(
                    destination = DEST_SEND_UPDATE_TABLE,
                    body = tableUpdateRequest.toString()
                )
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Failed to send status update", e)
            }
        }
    }

    fun disconnect() {
        serviceScope.launch {
            stompSession.disconnect()
        }
        serviceScope.cancel()
    }
}