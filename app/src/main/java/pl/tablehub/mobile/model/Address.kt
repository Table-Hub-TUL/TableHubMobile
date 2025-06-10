package pl.tablehub.mobile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Address(
    val id: Long,

    @SerialName("street_number")
    val streetNumber: Int,

    @SerialName("apartment_number")
    val apartmentNumber: Int?,

    val street: String,
    val city: String,

    @SerialName("postal_code")
    val postalCode: String,

    val country: String
) : Parcelable