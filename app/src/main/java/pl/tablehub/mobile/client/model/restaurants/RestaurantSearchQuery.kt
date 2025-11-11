package pl.tablehub.mobile.client.model.restaurants

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantSearchQuery(
    val cuisine: List<String>,
    val rating: Double,
    @SerialName("userLat")
    val userLatitude: Double,
    @SerialName("userLon")
    val userLongitude: Double,
    val radius: Double,
    @SerialName("restaurantAmount")
    val limit: Int,
    @SerialName("minFreeSeats")
    val minFreeSeats: Int? = null
) {
    constructor() : this(
        cuisine = emptyList(),
        rating = 0.0,
        userLatitude = 0.0,
        userLongitude = 0.0,
        radius = 0.0,
        limit = 0,
        minFreeSeats = null
    )
}