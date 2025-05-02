package pl.tablehub.mobile.model.websocket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.tablehub.mobile.model.Location

@Parcelize
data class RestaurantResponseDTO(
    val id: Long,
    val name: String,
    val address: String,
    val location: Location,
    val cuisine: List<String>,
    val rating: Double,
) : Parcelable