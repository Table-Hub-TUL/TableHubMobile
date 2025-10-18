package pl.tablehub.mobile.model.v1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: Long,
    val name: String,
    val address: Address,
    val location: Location,
    val cuisine: List<String>,
    val rating: Double,
    val sections: List<Section>
) : Parcelable
