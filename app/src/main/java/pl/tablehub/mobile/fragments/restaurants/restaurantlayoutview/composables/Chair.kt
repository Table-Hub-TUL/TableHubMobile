package pl.tablehub.mobile.fragments.restaurants.restaurantlayoutview.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chair(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(BASE_CHAIR_SIZE_DP.dp)
            .drawBehind {
                val width = size.width
                val height = size.height

                // Backrest
                drawRoundRect(
                    color = color.copy(alpha = 0.9f),
                    topLeft = Offset(width * 0.15f, 0f),
                    size = Size(width * 0.7f, height * 0.35f),
                    cornerRadius = CornerRadius(8f, 8f)
                )

                // Seat
                drawRoundRect(
                    color = color.copy(alpha = 0.7f),
                    topLeft = Offset(width * 0.1f, height * 0.3f),
                    size = Size(width * 0.8f, height * 0.5f),
                    cornerRadius = CornerRadius(6f, 6f)
                )

                // Left leg
                drawRoundRect(
                    color = color.copy(alpha = 0.9f),
                    topLeft = Offset(width * 0.15f, height * 0.75f),
                    size = Size(width * 0.15f, height * 0.25f),
                    cornerRadius = CornerRadius(3f, 3f)
                )

                // Right leg
                drawRoundRect(
                    color = color.copy(alpha = 0.9f),
                    topLeft = Offset(width * 0.7f, height * 0.75f),
                    size = Size(width * 0.15f, height * 0.25f),
                    cornerRadius = CornerRadius(3f, 3f)
                )

                // Highlight on backrest for depth
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset(width * 0.2f, height * 0.05f),
                    size = Size(width * 0.3f, height * 0.15f),
                    cornerRadius = CornerRadius(4f, 4f)
                )
            }
    )
}