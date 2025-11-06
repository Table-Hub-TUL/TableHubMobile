package pl.tablehub.mobile.model.v2

data class Reward(
    val id: Long,
    val title: String,
    val additionalDescription: String?,
    val image: Image,
    val restaurantName: String,
    val restaurantAddress: Address
)
