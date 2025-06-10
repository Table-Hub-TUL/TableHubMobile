package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Table(
    val id: Long,
    val positionX: Double,
    val positionY: Double,
    val capacity: Int,
    var status: TableStatus
) : Parcelable
