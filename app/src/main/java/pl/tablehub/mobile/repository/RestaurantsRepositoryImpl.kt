package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import pl.tablehub.mobile.client.model.restaurants.AggregateRestaurantStatus
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
    private val _specificRestaurantState = MutableStateFlow<RestaurantDetail?>(null)
    override val specificRestaurantState: StateFlow<RestaurantDetail?> = _specificRestaurantState.asStateFlow()

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
        if (_specificRestaurantState.value?.id == tableStatusChange.restaurantId) {
            val restaurant = _specificRestaurantState.value ?: return
            val newSections = restaurant.sections.map { section ->
                if (section.id == tableStatusChange.sectionId) {
                    val newTables = section.tables.map { table ->
                        if (table.id == tableStatusChange.tableId) {
                            table.copy(status = tableStatusChange.requestedStatus)
                        } else {
                            table
                        }
                    }
                    section.copy(tables = newTables)
                } else {
                    section
                }
            }
            _specificRestaurantState.value = restaurant.copy(sections = newSections)
        }
    }

    override suspend fun processTableStatusChange(tableStatusChange: AggregateRestaurantStatus) {
        val restaurant = _restaurantsMap.value[tableStatusChange.restaurantId] ?: return
        val newRestaurant = restaurant.copy(
            freeTableCount = tableStatusChange.freeTableCount,
            totalTableCount = tableStatusChange.totalTableCount
        )
        _restaurantsMap.value = _restaurantsMap.value.toMutableMap().apply {
            this[tableStatusChange.restaurantId] = newRestaurant
        }
    }
}