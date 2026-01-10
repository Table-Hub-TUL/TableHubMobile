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
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.gamification.rewards.RewardsEvent
import pl.tablehub.mobile.fragments.account.gamification.rewards.RewardsState
import pl.tablehub.mobile.model.v2.Address
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.shared.composables.BackButton
import pl.tablehub.mobile.ui.theme.*
import pl.tablehub.mobile.viewmodels.RewardsViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.times

@Composable
fun RewardsView(
    modifier: Modifier = Modifier,
    viewModel: RewardsViewModel,
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val dims = rememberGlobalDimensions()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is RewardsEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                RewardsEvent.RefreshRewards -> {
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SECONDARY_COLOR)
                .padding(paddingValues)
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
                        modifier = Modifier
                            .align(Alignment.TopStart)
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

            when (state) {
                RewardsState.Loading, RewardsState.Initial -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PRIMARY_COLOR)
                    }
                }

                is RewardsState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Błąd ładowania: ${(state as RewardsState.Error).message}",
                            color = Color.Red
                        )
                    }
                }

                is RewardsState.Success -> {
                    val rewards = (state as RewardsState.Success).rewards

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = dims.paddingHuge),
                        verticalArrangement = Arrangement.spacedBy(dims.paddingMedium)
                    ) {
                        items(rewards) { reward: Reward ->
                            RewardItem(
                                reward = reward,
                                onRedeemClick = { viewModel.redeemReward(reward.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RewardsViewPreview() {
    TableHubTheme {
        Text("Preview is adjusted to ViewModel dependency")
    }
}