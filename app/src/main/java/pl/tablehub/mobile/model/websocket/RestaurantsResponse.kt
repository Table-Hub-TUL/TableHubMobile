package pl.tablehub.mobile.model.websocket

import pl.tablehub.mobile.model.Restaurant

data class RestaurantsResponse(
    val queryParams: RestaurantsRequest,
    val restaurants: List<Restaurant>
) : MessageBody
