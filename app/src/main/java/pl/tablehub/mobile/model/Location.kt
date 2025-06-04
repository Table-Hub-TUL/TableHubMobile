package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Location(
    @SerialName("lng")
    val longitude: Double,
    @SerialName("lat")
    val latitude: Double
) : Parcelable
