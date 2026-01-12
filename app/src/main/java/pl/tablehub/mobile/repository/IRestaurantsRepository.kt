package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.StateFlow
import pl.tablehub.mobile.client.model.restaurants.AggregateRestaurantStatus
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.model.v2.Reward

interface IRestaurantsRepository {
    val restaurantsMap: StateFlow<Map<Long, RestaurantListItem>>
    val restaurantsFilters: StateFlow<RestaurantSearchQuery>
    val specificRestaurantState: StateFlow<RestaurantDetail?>
    val cuisines: StateFlow<List<String>>
    suspend fun processRestaurantList(dtos: List<RestaurantListItem>)
    suspend fun processTableStatusChange(tableStatusChange: TableStatusChange)
    suspend fun updateFilters(query: RestaurantSearchQuery)
    suspend fun processTableStatusChange(tableStatusChange: AggregateRestaurantStatus)
    suspend fun processCuisines(cuisines: List<String>)
    suspend fun setSpecificRestaurant(restaurant: RestaurantDetail)
    suspend fun getAllRestaurantsRewards(): List<Reward>
}