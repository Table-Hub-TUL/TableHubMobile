package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import pl.tablehub.mobile.model.TableStatus

@Serializable
@Parcelize
data class TableDetail(
    val id: Long,
    var tableStatus: TableStatus,
    val position: Point,
    val capacity: Int
) : Parcelable