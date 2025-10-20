package pl.tablehub.mobile.model.v2

data class RestaurantListItem(
    val id: Long,
    val name: String,
    val cuisine: List<String>,
    val address: Address,
    val location: Location,
    val rating: Double
    // TODO: make a field for WS mapping
)
