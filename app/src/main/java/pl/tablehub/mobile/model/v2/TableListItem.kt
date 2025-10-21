package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.TableStatus

@Serializable
@Parcelize
data class TableListItem(
    val restaurantId: Long,
    val id: Long,
    var tableStatus: TableStatus,
    val capacity: Int
) : Parcelable
