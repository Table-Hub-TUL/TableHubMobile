package pl.tablehub.mobile.fragments.account.gamification.rewards.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.ui.theme.*

@Composable
fun RewardItem(
    reward: Reward,
    isSelected: Boolean = false,
    onSelectionChange: (Boolean) -> Unit = {}
) {
    val dims = rememberGlobalDimensions()

    val borderColor = if (isSelected) TERTIARY_COLOR else Color.Gray
    val borderWidth = if (isSelected) 3.dp else 2.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                color = borderColor,
                width = borderWidth,
                shape = RoundedCornerShape(dims.buttonCornerRadius)
            )
            .clickable { onSelectionChange(!isSelected) }
            .padding(),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = CardDefaults.cardColors(containerColor = SECONDARY_COLOR),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.paddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(dims.iconSize)
                    .background(
                        color = TERTIARY_COLOR,
                        shape = RoundedCornerShape(dims.paddingSmall)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CardGiftcard,
                    contentDescription = "Reward icon",
                    tint = SECONDARY_COLOR,
                    modifier = Modifier.size(dims.iconSize * 0.6f)
                )
            }

            Spacer(modifier = Modifier.width(dims.paddingMedium))

            Text(
                text = reward.title,
                fontSize = dims.textSizeLarge,
                fontWeight = FontWeight.SemiBold,
                color = TERTIARY_COLOR
            )
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
                reward = Reward(1, "Voucher: 50zl"),
                isSelected = false
            )
            RewardItem(
                reward = Reward(2, "Voucher: 100zl"),
                isSelected = true
            )
        }
    }
}