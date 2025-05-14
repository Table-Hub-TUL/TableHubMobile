package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.Section

@Serializable
data class RestaurantSubscriptionInitialState(
    val id: Long,
    val sections: List<Section>
) : MessageBody
