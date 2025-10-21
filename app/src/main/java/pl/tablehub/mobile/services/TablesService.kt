package pl.tablehub.mobile.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange

import pl.tablehub.mobile.client.rest.interfaces.IRestaurantService
import pl.tablehub.mobile.client.websocket.service.WebSocketService
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.util.WSMessageRelay
import javax.inject.Inject

@AndroidEntryPoint
class TablesService : Service() {

    @Inject
    internal lateinit var repository: IRestaurantsRepository
    @Inject
    internal lateinit var webSocketService: WebSocketService
    @Inject
    internal lateinit var messageRelay: WSMessageRelay
    private val binder = LocalBinder()

    @Inject
    internal lateinit var restaurantClientService: IRestaurantService

    private val messageScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    @Inject
    lateinit var connectionScope: CoroutineScope

    inner class LocalBinder: Binder() {
        fun getService(): TablesService = this@TablesService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        fetchRestaurants()
        webSocketService.connectWebSocket()
        handleSubscriptions()
        return START_NOT_STICKY
    }

    private fun fetchRestaurants() {
        connectionScope.launch {
            val restaurants: List<RestaurantListItem> = restaurantClientService.fetchRestaurants(options = emptyMap<String, Any>())
            repository.processRestaurantList(restaurants)
        }
    }

    suspend fun getRestaurantById(id: Long): RestaurantDetail {
        return restaurantClientService.fetchRestaurant(id)
    }

    fun updateTableStatus(update: TableStatusChange) {
        connectionScope.launch {
            try {
                restaurantClientService.updateTableStatus(update)
            } catch (e: Exception) {
                Log.d("ERROR", e.message!!)
            }
        }
    }

    private fun handleSubscriptions() {
        messageRelay.messageFlow.onEach { message: String ->
            try {
                val decodedMessage = Json.decodeFromString<TableStatusChange>(message)
                repository.processTableStatusChange(decodedMessage)
            } catch (e: Exception) {
                Log.e("ERROR", e.message!!)
            }
        }.launchIn(messageScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketService.disconnect()
    }
}