package pl.tablehub.mobile.model.v2

data class Reward(
    val title: String,
    val additionalDescription: String?,
    val restaurantName: String,
    val restaurantAddress: Address
)
