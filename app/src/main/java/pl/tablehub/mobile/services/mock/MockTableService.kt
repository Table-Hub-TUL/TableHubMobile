package pl.tablehub.mobile.services.mock

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Position
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.Table
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionInitialState
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.TableUpdateResponse
import pl.tablehub.mobile.processing.interfaces.IWebsocketMessageProcessor
import pl.tablehub.mobile.services.interfaces.TablesService
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MockTableService : Service(), TablesService {

    @Inject
    internal lateinit var messageProcessor: IWebsocketMessageProcessor

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        messageProcessor.start()
        return START_NOT_STICKY
    }

    override fun updateTableStatus(requestParams: List<TableUpdateRequest>) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        messageProcessor.stop()
    }
}