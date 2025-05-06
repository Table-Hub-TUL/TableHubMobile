package pl.tablehub.mobile.services.websocket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.websocket.*
import pl.tablehub.mobile.model.websocket.TableUpdateRequest

@AndroidEntryPoint
class WebSocketService @Inject constructor(
    private val client: WebSocketClient
) : Service() {

    companion object ServiceContract {
        private const val SERVER_URL: String = "example.com"
        private const val DEBUG_TAG: String = "WEB_SOCKET"

        private const val DEST_SEND_UPDATE_TABLE = "/app/updateTableStatus"
        private const val DEST_SUBSCRIBE_INITIAL_STATUS = "/user/queue/initialStatus"
        private const val DEST_SUBSCRIBE_UPDATE_TABLE = "/user/queue/updateTableStatus"
    }

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var stompSession: StompSession

    override fun onCreate() {
        super.onCreate()
        serviceScope.launch {
            val stompClient: StompClient = StompClient(client)
            stompSession = stompClient.connect(SERVER_URL)
            subscribeToInitialStatus()
            subscribeToUpdateTableStatus()
        }
    }

    private suspend fun subscribeToInitialStatus() {
        stompSession.subscribe(DEST_SUBSCRIBE_INITIAL_STATUS)
    }

    private suspend fun subscribeToUpdateTableStatus() {
        stompSession.subscribe(DEST_SUBSCRIBE_UPDATE_TABLE)
    }

    fun sendStatusUpdate(tableUpdateRequest: TableUpdateRequest) {
        serviceScope.launch {
            stompSession.sendText(DEST_SEND_UPDATE_TABLE, tableUpdateRequest.toString())
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}