package pl.tablehub.mobile.model

data class Restaurant(
    val id: Long,
    val name: String,
    val address: String,
    val location: Location,
    val cuisine: List<String>,
    val rating: Double,
)
