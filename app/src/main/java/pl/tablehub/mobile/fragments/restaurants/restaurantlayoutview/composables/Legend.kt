package pl.tablehub.mobile.fragments.restaurants.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
private fun TableStatusBox(
    color: Color,
    textRes: Int
) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color)
    )
    Text(
        text = stringResource(textRes),
        color = TERTIARY_COLOR,
        fontSize = 12.sp, // TODO: Change it to scalable version
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun CompactLegend(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val tableStatuses = listOf(
            Color.Gray to R.string.unknown,
            Color.Red to R.string.occupied,
            GREEN_FREE_COLOR to R.string.free
        )
        tableStatuses.forEachIndexed { index, (color, textRes) ->
            TableStatusBox(
                color = color,
                textRes = textRes
            )
            if (index != tableStatuses.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}
