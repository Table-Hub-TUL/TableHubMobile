package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val url: String,
    val altText: String,
    val ratio: Double?
) : Parcelable
