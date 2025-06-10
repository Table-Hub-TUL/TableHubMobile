package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pl.tablehub.mobile.client.model.TableStatusChange
import pl.tablehub.mobile.model.Restaurant

interface IRestaurantsRepository {
    val restaurantsMap: StateFlow<Map<Long, Restaurant>>
    fun getRestaurantById(id: Long): Flow<Restaurant?>
    suspend fun processRestaurantList(dtos: List<Restaurant>)
    suspend fun processTableStatusChange(tableStatusChange: TableStatusChange)
}