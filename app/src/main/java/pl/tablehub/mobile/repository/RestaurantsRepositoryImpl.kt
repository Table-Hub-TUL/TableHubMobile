package pl.tablehub.mobile.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.client.model.restaurants.AggregateRestaurantStatus
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.client.rest.interfaces.IRestaurantService
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.Address
import pl.tablehub.mobile.model.v2.Image
import pl.tablehub.mobile.model.v2.Reward
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepositoryImpl @Inject constructor() : IRestaurantsRepository {

    @Inject
    lateinit var restaurantService: IRestaurantService

    private val _restaurantsMap = MutableStateFlow<Map<Long, RestaurantListItem>>(emptyMap())
    override val restaurantsMap: StateFlow<Map<Long, RestaurantListItem>> = _restaurantsMap.asStateFlow()
    private val _restaurantFilters = MutableStateFlow<RestaurantSearchQuery>(RestaurantSearchQuery())
    override val restaurantsFilters: StateFlow<RestaurantSearchQuery> = _restaurantFilters.asStateFlow()
    private val _specificRestaurantState = MutableStateFlow<RestaurantDetail?>(null)
    override val specificRestaurantState: StateFlow<RestaurantDetail?> = _specificRestaurantState.asStateFlow()
    private val _cuisines = MutableStateFlow<List<String>>(emptyList())
    override val cuisines: StateFlow<List<String>> = _cuisines.asStateFlow()

    override suspend fun processRestaurantList(dtos: List<RestaurantListItem>) {
        val oldMap = _restaurantsMap.value
        val updated = dtos.associateBy { dto ->
            dto.id
        }.mapValues { (id, newItem) ->
            val oldItem = oldMap[id]
            if (oldItem != null) {
                newItem.copy(
                    totalTableCount = oldItem.totalTableCount,
                    freeTableCount = oldItem.freeTableCount
                )
            } else {
                newItem
            }
        }
        _restaurantsMap.value = updated
    }


    override suspend fun setSpecificRestaurant(restaurant: RestaurantDetail) {
        _specificRestaurantState.value = restaurant
    }

    override suspend fun processTableStatusChange(tableStatusChange: TableStatusChange) {
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

    override suspend fun getAllRestaurantsRewards(): List<Reward> = coroutineScope {
        // 1. Ensure we have the list of restaurants
        var restaurants = _restaurantsMap.value.values.toList()
        if (restaurants.isEmpty()) {
            // We use the injected field here
            restaurants = restaurantService.fetchRestaurants(emptyMap())
            processRestaurantList(restaurants)
        }

        // 2. Fetch rewards for each restaurant in parallel
        val deferredRewards = restaurants.map { restaurant ->
            async {
                try {
                    restaurantService.fetchRestaurantRewards(restaurant.id)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }

        // 3. Aggregate and Map to Domain Model
        deferredRewards.awaitAll().flatten().map { dto ->
            Reward(
                id = dto.id,
                title = dto.title,
                additionalDescription = dto.additionalDescription,
                image = Image(
                    url = dto.image.url,
                    altText = dto.image.altText.toString(),
                    ratio = dto.image.ratio
                ),
                restaurantName = dto.restaurantName,
                restaurantAddress = Address(
                    street = dto.restaurantAddress.streetName,
                    streetNumber = dto.restaurantAddress.streetNumber,
                    apartmentNumber = dto.restaurantAddress.apartmentNumber,
                    city = dto.restaurantAddress.city,
                    postalCode = dto.restaurantAddress.postalCode,
                    country = dto.restaurantAddress.country
                ),
                redeemed = dto.redeemed
            )
        }
    }
}