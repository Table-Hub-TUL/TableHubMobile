package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Section(
    val id: Long,
    val name: String,
    val tables: List<Table>
) : Parcelable
