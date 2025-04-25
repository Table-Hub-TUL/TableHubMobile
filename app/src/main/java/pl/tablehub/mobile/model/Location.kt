package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val longitude: Double,
    val latitude: Double
) : Parcelable
