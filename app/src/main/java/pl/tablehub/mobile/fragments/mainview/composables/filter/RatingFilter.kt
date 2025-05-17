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

@Composable
fun RatingFilter(
    selectedRating: Double,
    onRatingChanged: (Double) -> Unit
) {
    Text(
        text = stringResource(R.string.minimum_rating) + ": ${"%.1f".format(selectedRating)}",
        color = TERTIARY_COLOR,
        fontSize = 16.sp
    )

    Slider(
        value = selectedRating.toFloat(),
        onValueChange = { onRatingChanged(it.toDouble()) },
        valueRange = 0f..5f,
        steps = 4,
        modifier = Modifier.padding(vertical = 8.dp),
        colors = SliderDefaults.colors(
            activeTrackColor = PRIMARY_COLOR,
            inactiveTrackColor = Color.LightGray,
            thumbColor = PRIMARY_COLOR
        )
    )
}