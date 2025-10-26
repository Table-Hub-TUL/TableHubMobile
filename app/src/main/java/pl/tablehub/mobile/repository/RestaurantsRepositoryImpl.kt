package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.client.rest.interfaces.IRestaurantService
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantService: IRestaurantService
) : IRestaurantsRepository {

    private val _restaurantsMap = MutableStateFlow<Map<Long, RestaurantListItem>>(emptyMap())
    override val restaurantsMap: StateFlow<Map<Long, RestaurantListItem>> = _restaurantsMap.asStateFlow()

    override suspend fun processRestaurantList(dtos: List<RestaurantListItem>) {
        val currentMap = _restaurantsMap.value.toMutableMap()
        dtos.forEach { dto ->
            currentMap[dto.id] = RestaurantListItem(
                id = dto.id,
                name = dto.name,
                address = dto.address,
                location = dto.location,
                cuisine = dto.cuisine,
                rating = dto.rating,
                tables = emptyList()
            )
        }
        _restaurantsMap.value = currentMap
    }

    override suspend fun processTableStatusChange(tableStatusChange: TableStatusChange) {
        // TODO: Wait for raju
    }

    override suspend fun searchRestaurants(query: RestaurantSearchQuery): Result<List<RestaurantListItem>> {
        return try {
            val restaurants = restaurantService.searchRestaurants(query)
            processRestaurantList(restaurants)
            Result.success(restaurants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}