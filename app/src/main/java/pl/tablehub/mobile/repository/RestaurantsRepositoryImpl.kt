package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepositoryImpl @Inject constructor() : IRestaurantsRepository {

    private val _restaurantsMap = MutableStateFlow<Map<Long, RestaurantListItem>>(emptyMap())
    override val restaurantsMap: StateFlow<Map<Long, RestaurantListItem>> = _restaurantsMap.asStateFlow()
    private val _restaurantFilters = MutableStateFlow<RestaurantSearchQuery>(RestaurantSearchQuery())
    override val restaurantsFilters: StateFlow<RestaurantSearchQuery> = _restaurantFilters.asStateFlow()

    override suspend fun processRestaurantList(dtos: List<RestaurantListItem>) {
        _restaurantsMap.value = dtos.associateBy { it.id }
    }
    override suspend fun processTableStatusChange(tableStatusChange: TableStatusChange) {
        // TODO: Wait for raju
    }

    override suspend fun updateFilters(query: RestaurantSearchQuery) {
        _restaurantFilters.value = query
    }
}