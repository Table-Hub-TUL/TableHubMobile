import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.tablehub.mobile.fragments.account.gamification.redeem.composables.RedeemButton
import pl.tablehub.mobile.fragments.account.gamification.redeem.composables.RedeemHeader
import pl.tablehub.mobile.fragments.account.gamification.redeem.composables.RestaurantInfo
import pl.tablehub.mobile.fragments.account.gamification.redeem.composables.RewardDetails
import pl.tablehub.mobile.fragments.account.gamification.redeem.composables.RewardImage
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun RedeemView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    reward: Reward,
    onRedeemClick: (Reward) -> Unit = {}
) {
    val dims = rememberGlobalDimensions()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
    ) {
        RedeemHeader(
            dims = dims,
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(dims.paddingBig)
        ) {
            RewardImage(reward, dims)

            Spacer(modifier = Modifier.height(dims.paddingBig))

            RewardDetails(reward, dims)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = dims.paddingMedium),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            RestaurantInfo(reward, dims)
        }

        RedeemButton(
            dims = dims,
            reward = reward,
            onRedeemClick = onRedeemClick
        )
    }
}
