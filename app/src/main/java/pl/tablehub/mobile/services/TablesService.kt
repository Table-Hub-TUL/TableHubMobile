package pl.tablehub.mobile.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.client.model.restaurants.AggregateRestaurantStatus
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
        observeFilterChanges()
        webSocketService.connectWebSocket()
        handleAggregateSubscriptions()
        fetchInitialData()
        return START_NOT_STICKY
    }

    private fun fetchInitialData() {
        connectionScope.launch {
            try {
                val cuisines = restaurantClientService.fetchCuisineList()
                repository.processCuisines(cuisines)
            } catch (e: Exception) {
                Log.e("TABLES_SERVICE", "Failed to fetch cuisine list", e)
            }
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

    fun subscribeToSpecificRestaurant(id: Long) {
        webSocketService.subscribeToSpecificRestaurantUpdate(id)
        handleSpecificSubscription()
    }

    fun unsubscribeSpecificRestaurant() {
        webSocketService.unsubscribeSpecificRestaurantUpdate()
    }

    private fun handleSpecificSubscription() {
        messageRelay.messageSpecificFlow.onEach { message: String ->
            try {
                val decodedMessage = Json.decodeFromString<TableStatusChange>(message)
                repository.processTableStatusChange(decodedMessage)
            } catch (e: Exception) {
                Log.e("ERROR", e.message!!)
            }
        }.launchIn(messageScope)
    }

    private fun handleAggregateSubscriptions() {
        messageRelay.messageAggregateFlow.onEach { message: String ->
            try {
                val decodedMessage = Json.decodeFromString<AggregateRestaurantStatus>(message)
                repository.processTableStatusChange(decodedMessage)
            } catch (e: Exception) {
                Log.e("ERROR", e.message!!)
            }
        }.launchIn(messageScope)
    }

    private fun observeFilterChanges() {
        connectionScope.launch {
            repository.restaurantsFilters.collect { filters ->
                val options = buildQueryMapFrom(filters)
                try {
                    val filteredRestaurants = restaurantClientService.fetchRestaurants(options)
                    repository.processRestaurantList(filteredRestaurants)
                } catch (e: Exception) {
                    Log.e("FILTER_FETCH", "Failed to fetch filtered restaurants", e)
                }
            }
        }
    }

    private fun buildQueryMapFrom(query: RestaurantSearchQuery): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["rating"] = query.rating
        map["userLat"] = query.userLatitude
        map["userLon"] = query.userLongitude
        map["radius"] = query.radius // You'll need to set a default radius
        map["limit"] = query.limit // You'll need to set a default limit
        if (query.cuisine.isNotEmpty()) {
            map["cuisine"] = query.cuisine.joinToString(",")
        }
        query.minFreeSeats?.let {
            if (it > 0) map["minFreeSeats"] = it
        }
        return map
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketService.disconnect()
    }
}