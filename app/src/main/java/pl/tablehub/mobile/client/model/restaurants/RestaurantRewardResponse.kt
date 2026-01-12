package pl.tablehub.mobile.client.model.restaurants

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantRewardResponse(
    val id: Long,
    val title: String,
    val additionalDescription: String?,
    val image: RewardImageDto,
    val restaurantName: String,
    val restaurantAddress: BackendAddressDto,
    val redeemed: Boolean,
    val cost: Int
)

@Serializable
data class RewardImageDto(
    val url: String,
    val altText: String?,
    val ratio: Double
)

@Serializable
data class BackendAddressDto(
    val streetNumber: Int,
    val streetName: String,
    val apartmentNumber: Int?,
    val city: String,
    val postalCode: String,
    val country: String
)