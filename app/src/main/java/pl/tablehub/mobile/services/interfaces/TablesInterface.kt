package pl.tablehub.mobile.services.interfaces

import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.TableUpdateResponse

@AndroidEntryPoint
interface TablesInterface {
    fun requestRestaurants(
        requestParams: RestaurantsRequest
    ) : RestaurantsResponse

    fun subscribeRestaurants (
        requestParams: List<Restaurant>
    ) : List<RestaurantSubscriptionResponse>

    fun unSubscribeRestaurants (
        requestParams: List<Restaurant>
    ) : Error?

    fun updateTableStatus (
        requestParams: List<TableUpdateRequest>
    ) : List<TableUpdateResponse>
}