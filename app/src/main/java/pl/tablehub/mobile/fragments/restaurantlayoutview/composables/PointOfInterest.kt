package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.model.v2.POI
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun PointOfInterest(
    poi: POI,
    modifier: Modifier = Modifier
) {
    val rawWidth = poi.bottomRight.x - poi.topLeft.x
    val rawHeight = poi.bottomRight.y - poi.topLeft.y
    val width = rawWidth.dp
    val height = rawHeight.dp
    val xOffset = poi.topLeft.x.dp
    val yOffset = poi.topLeft.y.dp
    val isVertical = rawHeight > rawWidth

    Box(
        modifier = modifier
            .offset(x = xOffset, y = yOffset)
            .size(width = width, height = height)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SECONDARY_COLOR,
                        SECONDARY_COLOR.copy(alpha = 0.8f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = TERTIARY_COLOR.copy(alpha = 0.3f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        val textModifier = if (isVertical) Modifier.graphicsLayer(rotationZ = -90f) else Modifier
        Text(
            text = poi.description,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = TERTIARY_COLOR,
            modifier = textModifier
        )
    }
}
