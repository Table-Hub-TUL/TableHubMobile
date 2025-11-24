package pl.tablehub.mobile.model.v2;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Achievement(
    val id: Long,
    val title: String,
    val points: Long
) : Parcelable





