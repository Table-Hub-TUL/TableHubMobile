package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionInitialState
import pl.tablehub.mobile.model.websocket.TableStatusChange

interface IRestaurantsRepository {
    val restaurantsMap: StateFlow<Map<Long, Restaurant>>
    fun getRestaurantById(id: Long): Flow<Restaurant?>
    suspend fun processInitialRestaurantData(restaurantId: Long, initialState: RestaurantSubscriptionInitialState)
    suspend fun processRestaurantList(dtos: List<RestaurantResponseDTO>)
    suspend fun processTableStatusChange(tableStatusChange: TableStatusChange)
    suspend fun addOrUpdateRestaurantFromDTO(dto: RestaurantResponseDTO, sections: List<pl.tablehub.mobile.model.Section> = emptyList())
}