package pl.tablehub.mobile.fragments.account.gamification.rewards.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.v2.Address
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.shared.composables.BackButton
import pl.tablehub.mobile.ui.theme.*

@Composable
fun RewardsView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onRedeemClick: (Reward) -> Unit = {},
    rewards: List<Reward> = rewardList
) {
    val dims = rememberGlobalDimensions()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
    ) {
        Card( /* Title card */
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dims.tinyCornerRadius),
            colors = CardDefaults.cardColors(containerColor = TERTIARY_COLOR),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BackButton(
                    onBackClick = onBackClick,
                    backgroundColor = TERTIARY_COLOR,
                    arrowColor = SECONDARY_COLOR,
                    modifier = Modifier.align(Alignment.TopStart)
                        .padding(top = dims.paddingMedium, start = dims.paddingMedium)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(vertical = 2 * dims.paddingHuge)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.gam_rewards),
                        fontSize = dims.textSizeHuge,
                        fontWeight = FontWeight.Bold,
                        color = SECONDARY_COLOR
                    )
                    Text(
                        text = stringResource(R.string.gam_redeem),
                        fontSize = dims.textSizeMedium,
                        fontWeight = FontWeight.Normal,
                        color = SECONDARY_COLOR
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(0.5 * dims.paddingHuge))
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = dims.paddingHuge),
            verticalArrangement = Arrangement.spacedBy(dims.paddingMedium)
        ) {
            items(rewards) { reward: Reward ->
                RewardItem(
                    reward = reward,
                    onRedeemClick = onRedeemClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RewardsViewPreview() {
    TableHubTheme {
        RewardsView()
    }
}