package pl.tablehub.mobile.services.implementation

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.processing.interfaces.IWebsocketMessageProcessor
import pl.tablehub.mobile.services.interfaces.TablesService
import pl.tablehub.mobile.services.websocket.WebSocketService
import javax.inject.Inject

@AndroidEntryPoint
class TablesServiceImplementation : Service(), TablesService {

    @Inject
    internal lateinit var messageProcessor: IWebsocketMessageProcessor

    @Inject
    internal lateinit var webSocketService: WebSocketService

    private val binder = LocalBinder()

    inner class LocalBinder: Binder() {
        fun getService(): TablesServiceImplementation = this@TablesServiceImplementation
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        webSocketService.connectWebSocket()
        messageProcessor.start()
        return START_NOT_STICKY
    }

    override fun updateTableStatus(requestParams: TableUpdateRequest) {
        webSocketService.sendStatusUpdate(requestParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        messageProcessor.stop()
        webSocketService.disconnect()
    }
}