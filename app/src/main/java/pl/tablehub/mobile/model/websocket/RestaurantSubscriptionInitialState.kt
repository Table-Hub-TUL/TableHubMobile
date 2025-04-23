package pl.tablehub.mobile.model.websocket

import pl.tablehub.mobile.model.Section

data class RestaurantSubscriptionInitialState(
    val id: Long,
    val section: List<Section>
) : MessageBody
