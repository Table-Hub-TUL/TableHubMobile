package pl.tablehub.mobile.services.implementation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        webSocketService.connectWebSocket()
        messageProcessor.start()
        return START_NOT_STICKY
    }

    override fun requestRestaurants(requestParams: RestaurantsRequest): RestaurantsResponse {
        TODO("Not yet implemented")
    }

    override fun subscribeRestaurants(requestParams: List<RestaurantResponseDTO>): List<RestaurantSubscriptionResponse> {
        TODO("Not yet implemented")
    }

    override fun unSubscribeRestaurants(requestParams: List<RestaurantResponseDTO>): Error? {
        TODO("Not yet implemented")
    }

    override fun updateTableStatus(requestParams: List<TableUpdateRequest>) {
        requestParams.forEach { param -> webSocketService.sendStatusUpdate(param) }
    }

    override fun onDestroy() {
        super.onDestroy()
        messageProcessor.stop()
        webSocketService.disconnect()
    }
}