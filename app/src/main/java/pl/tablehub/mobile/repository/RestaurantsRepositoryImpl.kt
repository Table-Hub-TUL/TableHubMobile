package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.client.model.restaurants.AggregateRestaurantStatus
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.model.TableStatus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepositoryImpl @Inject constructor() : IRestaurantsRepository {

    private val _restaurantsMap = MutableStateFlow<Map<Long, RestaurantListItem>>(emptyMap())
    override val restaurantsMap: StateFlow<Map<Long, RestaurantListItem>> = _restaurantsMap.asStateFlow()
    private val _restaurantFilters = MutableStateFlow<RestaurantSearchQuery>(RestaurantSearchQuery())
    override val restaurantsFilters: StateFlow<RestaurantSearchQuery> = _restaurantFilters.asStateFlow()
    private val _specificRestaurantState = MutableStateFlow<RestaurantDetail?>(null)
    override val specificRestaurantState: StateFlow<RestaurantDetail?> = _specificRestaurantState.asStateFlow()
    private val _cuisines = MutableStateFlow<List<String>>(emptyList())
    override val cuisines: StateFlow<List<String>> = _cuisines.asStateFlow()

    override suspend fun processRestaurantList(dtos: List<RestaurantListItem>) {
        val specificRestaurant = _specificRestaurantState.value

        val updatedDtos = if (specificRestaurant != null) {
            dtos.map { restaurant ->
                // If the incoming restaurant matches the one we are holding in detail memory
                if (restaurant.id == specificRestaurant.id) {
                    // Calculate the REAL count from our detailed local state
                    val calculatedCount = specificRestaurant.sections
                        .flatMap { it.tables }
                        .count { table -> table.status == TableStatus.AVAILABLE }

                    // Override the server's (potentially stale) count with our fresh local count
                    restaurant.copy(freeTableCount = calculatedCount)
                } else {
                    restaurant
                }
            }
        } else {
            dtos
        }

        _restaurantsMap.value = updatedDtos.associateBy { it.id }
    }

    override suspend fun setSpecificRestaurant(restaurant: RestaurantDetail) {
        _specificRestaurantState.value = restaurant
    }

    override suspend fun processTableStatusChange(tableStatusChange: TableStatusChange) {/*
        val currentDetail = _specificRestaurantState.value

        // We can only recalculate based on tables if we have the full details (sections/tables) loaded
        if (currentDetail?.id == tableStatusChange.restaurantId) {

            // 1. Create the new list of sections with the updated table status
            val newSections = currentDetail.sections.map { section ->
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

            // 2. Update the specific restaurant state with the new structure
            val updatedDetail = currentDetail.copy(sections = newSections)
            _specificRestaurantState.value = updatedDetail

            // 3. Recalculate the absolute Free Table Count from the updated data
            // We sum the count of available tables across all sections
            val calculatedFreeCount = newSections.sumOf { section ->
                section.tables.count { it.status == TableStatus.AVAILABLE }
            }

            // 4. Update the Map State (List View) with the fresh, recalculated count
            val restaurantListItem = _restaurantsMap.value[tableStatusChange.restaurantId]
            if (restaurantListItem != null) {
                val updatedRestaurantListItem = restaurantListItem.copy(
                    freeTableCount = calculatedFreeCount
                )

                _restaurantsMap.value = _restaurantsMap.value.toMutableMap().apply {
                    this[tableStatusChange.restaurantId] = updatedRestaurantListItem
                }
            }
        }
        */
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

    override suspend fun processCuisines(cuisines: List<String>) {
        _cuisines.value = cuisines
    }

    override suspend fun updateFilters(query: RestaurantSearchQuery) {
        _restaurantFilters.value = query
    }
}