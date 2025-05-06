package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantSubscriptionResponse(
    val restaurantId: Long,
    val success: Boolean,
    val message: String?,
    val initialState: RestaurantSubscriptionInitialState
) : MessageBody
