package pl.tablehub.mobile.services.websocket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.websocket.*

@AndroidEntryPoint
class WebSocketService @Inject constructor(
    private val client: WebSocketClient
) : Service() {

    companion object ServiceContract {
        private const val SERVER_URL: String = "example.com"
        private const val DEBUG_TAG: String = "WEB_SOCKET"

        private const val DEST_SEND_INITIAL_STATUS = "/app/initialStatus"
        private const val DEST_SEND_UPDATE_TABLE = "/app/updateTableStatus"
        private const val DEST_SUBSCRIBE_INITIAL_STATUS = "/user/queue/initialStatus"
        private const val DEST_SUBSCRIBE_UPDATE_TABLE = "/user/queue/updateTableStatus"
    }

    init {
        val stompClient = StompClient(client)
        //val session: StompSession = stompClient.connect(SERVER_URL)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}