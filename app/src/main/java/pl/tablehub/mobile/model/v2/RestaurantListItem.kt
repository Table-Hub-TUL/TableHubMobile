package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.jvm.Transient

@Serializable
@Parcelize
data class RestaurantListItem(
    val id: Long,
    val name: String,
    val cuisine: List<String>,
    val address: Address,
    val location: Location,
    val rating: Double,
) : Parcelable {
    @Transient
    var tables: List<TableListItem>? = null
}
