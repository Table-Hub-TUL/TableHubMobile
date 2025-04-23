package pl.tablehub.mobile.model.websocket

data class RestaurantSubscriptionResponse(
    val restaurantId: Long,
    val success: Boolean,
    val message: String?,
    val initialState: RestaurantSubscriptionInitialState
) : MessageBody
