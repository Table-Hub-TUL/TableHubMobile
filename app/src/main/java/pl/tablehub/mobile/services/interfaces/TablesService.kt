package pl.tablehub.mobile.services.interfaces

import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.TableUpdateResponse

interface TablesService {
    fun requestRestaurants(
        requestParams: RestaurantsRequest
    ) : RestaurantsResponse

    fun subscribeRestaurants (
        requestParams: List<RestaurantResponseDTO>
    ) : List<RestaurantSubscriptionResponse>

    fun unSubscribeRestaurants (
        requestParams: List<RestaurantResponseDTO>
    ) : Error?

    fun updateTableStatus (
        requestParams: List<TableUpdateRequest>
    )
}