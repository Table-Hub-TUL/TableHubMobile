package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Address(
    val streetNumber: Int,
    val apartmentNumber: Int?,
    val street: String,
    val city: String,
    val postalCode: String,
    val country: String
) : Parcelable {
    override fun toString(): String {
        val apartmentPart = apartmentNumber?.let { "/$it" } ?: ""
        return "$street $streetNumber$apartmentPart, $postalCode $city, $country"
    }
}
