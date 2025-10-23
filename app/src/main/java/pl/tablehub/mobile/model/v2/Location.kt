package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Location(
    @SerializedName("lng")
    val longitude: Double,

    @SerializedName("lat")
    val latitude: Double
) : Parcelable