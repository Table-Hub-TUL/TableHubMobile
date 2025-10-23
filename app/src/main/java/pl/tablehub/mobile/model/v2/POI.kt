package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class POI(
    val description: String,
    val topLeft: Point,
    val bottomRight: Point,
) : Parcelable
