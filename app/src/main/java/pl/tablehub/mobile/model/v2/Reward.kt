package pl.tablehub.mobile.model.v2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reward(
    val id: Long,
    val title: String,
    val additionalDescription: String?,
    val image: Image,
    val restaurantName: String,
    val restaurantAddress: Address,
    val redeemed: Boolean
) : Parcelable
