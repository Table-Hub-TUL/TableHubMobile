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
    val limit: Int
)