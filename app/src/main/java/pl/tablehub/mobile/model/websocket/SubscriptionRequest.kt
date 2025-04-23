package pl.tablehub.mobile.model.websocket

data class SubscriptionRequest(
    val restaurantId: Long
) : MessageBody
