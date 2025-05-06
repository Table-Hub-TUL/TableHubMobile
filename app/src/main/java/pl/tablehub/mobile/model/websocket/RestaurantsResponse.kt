package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsResponse(
    val queryParams: RestaurantsRequest,
    val restaurants: List<RestaurantResponseDTO>
) : MessageBody
