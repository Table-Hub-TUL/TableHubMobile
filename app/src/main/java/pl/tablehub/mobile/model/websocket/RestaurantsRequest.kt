package pl.tablehub.mobile.model.websocket

import pl.tablehub.mobile.model.Location

data class RestaurantsRequest  (
    val localization: Location,
    val radius: Double,
    val filters: List<String>
) : MessageBody