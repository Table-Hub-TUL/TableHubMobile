package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class RestaurantListItem(
    val id: Long,
    val name: String,
    val cuisine: List<String>,
    val address: Address,
    val location: Location,
    val rating: Double,
    val tables: List<TableListItem> = emptyList(),
    val totalTableCount: Int = 0,
    val freeTableCount: Int = 0
) : Parcelable