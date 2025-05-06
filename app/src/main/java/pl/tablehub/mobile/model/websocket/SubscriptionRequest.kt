package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionRequest(
    val restaurantId: Long
) : MessageBody
