package pl.tablehub.mobile.fragments.restaurants.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
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
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun StateLegend(
    modifier: Modifier = Modifier
) {
    val dims = rememberGlobalDimensions()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dims.paddingBig, vertical = dims.paddingSmall)
            .background(SECONDARY_COLOR),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem(status = stringResource(R.string.unknown), color = Color.Gray)

        LegendItem(status = stringResource(R.string.occupied), color = Color.Red)

        LegendItem(status = stringResource(R.string.free), color = GREEN_FREE_COLOR)
    }
}

@Composable
fun LegendItem(status: String, color: Color) {
    val dims = rememberGlobalDimensions()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dims.smallSpacing/2)
    ) {
        Spacer(
            modifier = Modifier
                .width(dims.contentIconSize)
                .height(dims.contentIconSize)
                .clip(RoundedCornerShape(dims.tinyCornerRadius))
                .background(color)
        )

        Text(
            text = status,
            color = TERTIARY_COLOR,
            fontSize = dims.textSizeBig,
            fontWeight = FontWeight.Medium
        )
    }
}