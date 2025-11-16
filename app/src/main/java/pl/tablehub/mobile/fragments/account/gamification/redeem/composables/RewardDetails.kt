package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.GlobalDimensions
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
internal fun RewardDetails(
    reward: Reward,
    dims: GlobalDimensions
) {
    Text(
        text = reward.title,
        fontSize = dims.textSizeHeader,
        fontWeight = FontWeight.Bold,
        color = TERTIARY_COLOR
    )

    Spacer(modifier = Modifier.height(dims.paddingSmall))

    reward.additionalDescription?.let { description ->
        Text(
            text = description,
            fontSize = dims.textSizeMedium,
            color = TERTIARY_COLOR.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(dims.paddingLarge))
    }
}
