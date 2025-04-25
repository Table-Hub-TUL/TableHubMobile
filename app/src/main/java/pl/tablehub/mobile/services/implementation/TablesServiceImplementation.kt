package pl.tablehub.mobile.services.implementation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.TableUpdateResponse
import pl.tablehub.mobile.services.interfaces.TablesService

class TablesServiceImplementation : Service(), TablesService {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun requestRestaurants(requestParams: RestaurantsRequest): RestaurantsResponse {
        TODO("Not yet implemented")
    }

    override fun subscribeRestaurants(requestParams: List<Restaurant>): List<RestaurantSubscriptionResponse> {
        TODO("Not yet implemented")
    }

    override fun unSubscribeRestaurants(requestParams: List<Restaurant>): Error? {
        TODO("Not yet implemented")
    }

    override fun updateTableStatus(requestParams: List<TableUpdateRequest>): List<TableUpdateResponse> {
        TODO("Not yet implemented")
    }
}