package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.GlobalDimensions
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
internal fun RestaurantInfo(
    reward: Reward,
    dims: GlobalDimensions
) {
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
