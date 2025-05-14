package pl.tablehub.mobile.services.websocket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.websocket.*
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.WebSocketMessage
import pl.tablehub.mobile.util.WSMessageRelay
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketService @Inject constructor(
    private val client: WebSocketClient,
    private val messageRelay: WSMessageRelay
) {

    companion object ServiceContract {
        private const val SERVER_URL: String = "ws://10.0.2.2:8080/ws"
        private const val DEBUG_TAG: String = "WEB_SOCKET"
        private const val DEST_SEND_UPDATE_TABLE = "/app/updateTableStatus"
        private const val DEST_SUBSCRIBE_INITIAL_STATUS = "/user/queue/initialStatus"
        private const val DEST_SUBSCRIBE_UPDATE_TABLE = "/user/queue/updateTableStatus"
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var stompSession: StompSession
    fun connectWebSocket() {
        serviceScope.launch {
            try {
                val stompClient = StompClient(client)
                stompSession = stompClient.connect(url = SERVER_URL)
                Log.d(DEBUG_TAG, "Connected")
                subscribeToInitialStatus()
                subscribeToUpdateTableStatus()
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "WebSocket connection failed", e)
            }
        }
    }

    private fun subscribeToInitialStatus() {
        serviceScope.launch {
            try {
                stompSession.subscribe(DEST_SUBSCRIBE_INITIAL_STATUS).collect { frame ->
                    val body = frame.bodyAsText
                    val subscriptionResponse = Json.decodeFromString<WebSocketMessage>(body)
                    messageRelay.emitMessage(subscriptionResponse)
                }
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Initial status subscription failed", e)
            }
        }
    }

    private fun subscribeToUpdateTableStatus() {
        serviceScope.launch {
            try {
                stompSession.subscribe(DEST_SUBSCRIBE_UPDATE_TABLE).collect { frame ->
                    val body = frame.bodyAsText
                    val updateResponse = Json.decodeFromString<WebSocketMessage>(body)
                    messageRelay.emitMessage(updateResponse)
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