package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.Location

@Serializable
data class RestaurantsRequest  (
    val localization: Location,
    val radius: Double,
    val filters: List<String>
) : MessageBody