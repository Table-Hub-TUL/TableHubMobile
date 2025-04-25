package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Table(
    val id: Long,
    val position: Position,
    val capacity: Int,
    val status: TableStatus
) : Parcelable
