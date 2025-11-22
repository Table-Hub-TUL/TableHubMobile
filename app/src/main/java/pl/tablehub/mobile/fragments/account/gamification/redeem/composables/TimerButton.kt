package pl.tablehub.mobile.fragments.account.gamification.redeem.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.profile.composables.PrimaryActionButton
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.theme.GlobalDimensions
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.WHITE_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
internal fun TimerButton(
    dims: GlobalDimensions,
    redeemedUntil: Long?,
    onTimerExpired: () -> Unit
) {
    var timeString by remember { mutableStateOf("00:00") }

    LaunchedEffect(key1 = redeemedUntil) {
        val expiryTime = redeemedUntil ?: 0L

        while (isActive) {
            val currentTime = System.currentTimeMillis()
            val remainingTimeInMillis = (expiryTime - currentTime).coerceAtLeast(0L)

            if (remainingTimeInMillis == 0L) {
                timeString = "00:00"
                if (expiryTime != 0L) {
                    onTimerExpired()
                }
                break
            }

            val minutes = (remainingTimeInMillis / 1000) / 60
            val seconds = (remainingTimeInMillis / 1000) % 60
            timeString = String.format("%02d:%02d", minutes, seconds)

            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dims.paddingBig)
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = dims.paddingLarge)
                .height(dims.buttonHeight),
            shape = RoundedCornerShape(dims.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = TERTIARY_COLOR,
                disabledContainerColor = TERTIARY_COLOR.copy(alpha = 0.7f)
            ),
            enabled = false
        ) {
            Text(
                text = timeString,
                fontSize = dims.textSizeMedium,
                color = WHITE_COLOR,
                fontWeight = FontWeight.Bold
            )
        }
    }
}