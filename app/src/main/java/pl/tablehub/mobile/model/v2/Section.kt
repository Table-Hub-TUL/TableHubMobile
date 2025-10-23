package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Section(
    val id: Long,
    val name: String,
    val tableDetails: List<TableDetail>,
    val pois: List<POI>,
    val sectionLayout: SectionLayout
) : Parcelable
