package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.TableStatus

@Serializable
data class TableStatusChange(
    val restaurantId: Long,
    val sectionId: Long,
    val tableId: Long,
    val newStatus: TableStatus,
    val changeTimestamp: Long
) : MessageBody
