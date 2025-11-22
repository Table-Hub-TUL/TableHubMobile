package pl.tablehub.mobile.fragments.account.gamification.rewards.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.*

@Composable
fun RewardItem(
    reward: Reward,
    onRedeemClick: (Reward) -> Unit = {}
) {
    val dims = rememberGlobalDimensions()
    val borderColor = PRIMARY_COLOR
    val borderWidth = 4.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                color = borderColor,
                width = borderWidth,
                shape = RoundedCornerShape(dims.buttonCornerRadius)
            )
            .clickable { onRedeemClick(reward) },
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dims.paddingMedium),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = reward.image.url,
                    contentDescription = reward.image.altText,
                    modifier = Modifier
                        .size(1.5 * dims.bigIconSize)
                        .clip(RoundedCornerShape(dims.paddingSmall))
                        .background(TERTIARY_COLOR.copy(alpha = 0.1f)),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(dims.paddingMedium))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dims.paddingSmall)
                ) {
                    Text(
                        text = reward.title,
                        fontSize = dims.textSizeMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    reward.additionalDescription?.let { description ->
                        Text(
                            text = description,
                            fontSize = dims.textSizeSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = dims.paddingMedium),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dims.paddingMedium)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = reward.restaurantName,
                    fontSize = dims.textSizeMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = stringResource(R.string.location),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.size(dims.iconSize * 0.7f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${reward.restaurantAddress.street}, ${reward.restaurantAddress.city}",
                        fontSize = dims.textSizeSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RewardItemPreview() {
    TableHubTheme {
        Column(
            modifier = Modifier
                .background(SECONDARY_COLOR)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RewardItem(
                reward = rewardList[0],
            )
            RewardItem(
                reward = rewardList[1],
            )
        }
    }
}