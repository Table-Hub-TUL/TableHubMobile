package pl.tablehub.mobile.fragments.mainview.composables.filter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import kotlin.math.roundToInt

@Composable
fun TableFilterSlider(
    minFreeSeats: Int,
    onMinFreeSeatsChanged: (Int) -> Unit
) {
    Text(
        text = stringResource(R.string.minimum_free_seats) + ": $minFreeSeats",
        fontSize = 16.sp,
        color = TERTIARY_COLOR
    )
    Slider(
        value = minFreeSeats.toFloat(),
        onValueChange = { onMinFreeSeatsChanged(it.roundToInt()) },
        valueRange = 0f..7f,
        steps = 6,
        modifier = Modifier.padding(vertical = 8.dp),
        colors = SliderDefaults.colors(
            activeTrackColor = PRIMARY_COLOR,
            inactiveTrackColor = Color.LightGray,
            thumbColor = PRIMARY_COLOR
        )
    )
}