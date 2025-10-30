package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.StateFlow
import pl.tablehub.mobile.client.model.restaurants.AggregateRestaurantStatus
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem

interface IRestaurantsRepository {
    val restaurantsMap: StateFlow<Map<Long, RestaurantListItem>>
    val specificRestaurantState: StateFlow<RestaurantDetail?>
    suspend fun processRestaurantList(dtos: List<RestaurantListItem>)
    suspend fun processTableStatusChange(tableStatusChange: TableStatusChange)
    suspend fun processTableStatusChange(tableStatusChange: AggregateRestaurantStatus)
}