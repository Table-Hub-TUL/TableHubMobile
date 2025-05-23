package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.TableStatus

@Serializable
data class TableUpdateResponse(
    val restaurantId: Long,
    val sectionId: Long,
    val tableId: Long,
    val updateSuccess: Boolean,
    val resultingStatus: TableStatus,
    val message: String?,
    val pointsAwarded: Int
) : MessageBody
