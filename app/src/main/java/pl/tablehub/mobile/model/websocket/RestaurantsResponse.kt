package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsResponse(
    val restaurants: List<RestaurantResponseDTO>
) : MessageBody
