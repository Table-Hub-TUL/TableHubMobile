package pl.tablehub.mobile.model.v2

data class Address(
    val streetNumber: Int,
    val apartmentNumber: Int?,
    val streetName: String,
    val city: String,
    val postalCode: String,
    val country: String
)
