package pl.tablehub.mobile.client.model.restaurants
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AggregateRestaurantStatus (
    val restaurantId: Long,
    val name: String,
    val freeTableCount: Int,
    val totalTableCount: Int,
    val timestamp: Instant
)