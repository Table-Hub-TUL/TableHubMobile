package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.gamification.rewards.composables.rewardList
import pl.tablehub.mobile.fragments.account.profile.composables.PrimaryActionButton
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.shared.composables.BackButton
import pl.tablehub.mobile.ui.theme.*

@Composable
fun RedeemView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    reward: Reward,
    onRedeemClick: (Reward) -> Unit = {}
){
    val dims = rememberGlobalDimensions()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
    ) {
        Card(
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

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(dims.paddingBig)
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

            Spacer(modifier = Modifier.height(dims.paddingBig))

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

            HorizontalDivider(
                modifier = Modifier.padding(vertical = dims.paddingMedium),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            Text(
                text = reward.restaurantName,
                fontSize = dims.textSizeLarge,
                fontWeight = FontWeight.SemiBold,
                color = TERTIARY_COLOR
            )

            Spacer(modifier = Modifier.height(dims.paddingSmall))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(R.string.location),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(dims.iconSize)
                )
                Spacer(modifier = Modifier.width(dims.paddingSmall))
                Text(
                    text = "${reward.restaurantAddress.street}, ${reward.restaurantAddress.city}",
                    fontSize = dims.textSizeMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.paddingBig)
        ) {
            PrimaryActionButton(
                text = stringResource(R.string.confirm),
                onClick = { onRedeemClick(reward) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RedeemViewPreview() {
    TableHubTheme {
        RedeemView(
            reward = rewardList[0]
        )
    }
}
