package pl.tablehub.mobile.model.websocket

import pl.tablehub.mobile.model.TableStatus

data class TableUpdateRequest(
    val restaurantId: Long,
    val sectionId: Long,
    val tableId: Long,
    val requestedStatus: TableStatus
) : MessageBody
