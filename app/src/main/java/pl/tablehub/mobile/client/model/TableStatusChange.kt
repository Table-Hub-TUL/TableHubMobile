package pl.tablehub.mobile.client.model

import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.TableStatus

@Serializable
data class TableStatusChange(
    val restaurantId: Long,
    val sectionId: Long,
    val tableId: Long,
    val requestedStatus: TableStatus
)
