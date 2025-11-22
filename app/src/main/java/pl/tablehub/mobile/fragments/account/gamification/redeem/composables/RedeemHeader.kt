package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.shared.composables.BackButton
import pl.tablehub.mobile.ui.theme.GlobalDimensions
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
internal fun RedeemHeader(
    dims: GlobalDimensions,
    onBackClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dims.tinyCornerRadius),
        colors = CardDefaults.cardColors(containerColor = TERTIARY_COLOR),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

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
                    .padding(vertical = 2 * dims.paddingHuge),
                horizontalAlignment = Alignment.CenterHorizontally
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
}
