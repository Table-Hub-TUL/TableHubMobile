package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Position(
    val x: Double,
    val y: Double
) : Parcelable
