package pl.tablehub.mobile.model.v2

data class RewardDto(
    val id: Long,
    val title: String,
    val additionalDescription: String?,
    val image: String,
    val restaurantName: String,
    val street: String,
    val city: String,
    val redeemed: Boolean
)