package pl.tablehub.mobile.model.websocket

data class RestaurantsResponse(
    val queryParams: RestaurantsRequest,
    val restaurants: List<RestaurantResponseDTO>
) : MessageBody
