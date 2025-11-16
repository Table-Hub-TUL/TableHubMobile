package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.GlobalDimensions
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
internal fun RewardImage(
    reward: Reward,
    dims: GlobalDimensions
) {
    AsyncImage(
        model = reward.image.url,
        contentDescription = reward.image.altText,
        modifier = Modifier
            .fillMaxWidth()
            .height((dims.logoSize / 1.5f).dp)
            .clip(RoundedCornerShape(dims.buttonCornerRadius))
            .background(TERTIARY_COLOR.copy(alpha = 0.1f)),
        contentScale = ContentScale.Crop
    )
}
