package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionInitialState
import pl.tablehub.mobile.model.websocket.TableStatusChange
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepositoryImpl @Inject constructor() : IRestaurantsRepository {

    private val _restaurantsMap = MutableStateFlow<Map<Long, Restaurant>>(emptyMap())
    override val restaurantsMap: StateFlow<Map<Long, Restaurant>> = _restaurantsMap.asStateFlow()

    override fun getRestaurantById(id: Long): Flow<Restaurant?> =
        _restaurantsMap.mapNotNull { it[id] }

    override suspend fun processRestaurantList(dtos: List<RestaurantResponseDTO>) {
        val currentMap = _restaurantsMap.value.toMutableMap()
        dtos.forEach { dto ->
            currentMap[dto.id] = Restaurant(
                id = dto.id,
                name = dto.name,
                address = dto.address,
                location = dto.location,
                cuisine = dto.cuisine,
                rating = dto.rating,
                sections = currentMap[dto.id]?.sections ?: emptyList()
            )
        }
        _restaurantsMap.value = currentMap
    }

    override suspend fun addOrUpdateRestaurantFromDTO(dto: RestaurantResponseDTO, sections: List<Section>) {
        val currentMap = _restaurantsMap.value.toMutableMap()
        currentMap[dto.id] = Restaurant(
            id = dto.id,
            name = dto.name,
            address = dto.address,
            location = dto.location,
            cuisine = dto.cuisine,
            rating = dto.rating,
            sections = sections
        )
        _restaurantsMap.value = currentMap
    }

    override suspend fun processInitialRestaurantData(restaurantId: Long, initialState: RestaurantSubscriptionInitialState) {
        val currentMap = _restaurantsMap.value.toMutableMap()
        currentMap[restaurantId]?.let { existingRestaurant ->
            currentMap[restaurantId] = existingRestaurant.copy(sections = initialState.sections)
            _restaurantsMap.value = currentMap
        }
    }

    override suspend fun processTableStatusChange(tableStatusChange: TableStatusChange) {
        val currentMap = _restaurantsMap.value.toMutableMap()
        currentMap[tableStatusChange.restaurantId]?.let { restaurant ->
            val updatedSections = restaurant.sections.map { section ->
                if (section.id == tableStatusChange.sectionId) {
                    section.copy(tables = section.tables.map { table ->
                        if (table.id == tableStatusChange.tableId) {
                            table.copy(status = tableStatusChange.newStatus)
                        } else {
                            table
                        }
                    })
                } else {
                    section
                }
            }
            currentMap[tableStatusChange.restaurantId] = restaurant.copy(sections = updatedSections)
            _restaurantsMap.value = currentMap
        }
    }
}