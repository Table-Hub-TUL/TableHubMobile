package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Section(
    val id: Long,
    val name: String,
    val tables: List<Table>
) : Parcelable
