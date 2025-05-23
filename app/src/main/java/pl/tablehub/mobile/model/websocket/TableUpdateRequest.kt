package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.TableStatus

@Serializable
data class TableUpdateRequest(
    val restaurantId: Long,
    val sectionId: Long,
    val tableId: Long,
    val requestedStatus: TableStatus
) : MessageBody
