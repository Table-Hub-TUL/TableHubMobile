package pl.tablehub.mobile.client.websocket.service

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.websocket.*
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.util.Constants.BACKEND_IP
import pl.tablehub.mobile.util.WSMessageRelay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketService @Inject constructor(
    private val client: WebSocketClient,
    private val messageRelay: WSMessageRelay
) {

    companion object ServiceContract {
        private const val SERVER_URL: String = "wss://${BACKEND_IP}/ws"
        private const val DEBUG_TAG: String = "WEB_SOCKET"
        private const val DEST_SUBSCRIBE_UPDATE_TABLE = "/topic/table-updates"
    }

    @Inject
    lateinit var serviceScope: CoroutineScope
    private lateinit var stompSession: StompSession

    @Inject
    lateinit var dataStore: EncryptedDataStore

    fun connectWebSocket() {
        serviceScope.launch {
            try {
                val token = dataStore.getJWT().first()!!
                val urlWithToken = "$SERVER_URL?token=$token"
                val stompClient = StompClient(client)
                stompSession = stompClient.connect(url = urlWithToken)
                subscribeToUpdateTableStatus()
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "WebSocket connection failed", e)
            }
        }
    }

    private fun subscribeToUpdateTableStatus() {
        serviceScope.launch {
            try {
                stompSession.subscribe(DEST_SUBSCRIBE_UPDATE_TABLE).collect { frame ->
                    val body = frame.bodyAsText
                    messageRelay.emitMessage(body)
                }
            } catch (e: Exception) {
                Log.e(DEBUG_TAG, "Table update subscription failed", e)
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