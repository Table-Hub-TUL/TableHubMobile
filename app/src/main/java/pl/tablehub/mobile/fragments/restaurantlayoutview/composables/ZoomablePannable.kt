import android.util.Log
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Stable
class ZoomPanState {
    var scale by mutableFloatStateOf(1f)
    var offset by mutableStateOf(Offset.Zero)
}

@Composable
fun rememberZoomPanState(): ZoomPanState {
    return remember { ZoomPanState() }
}

private fun calculateZoomCentroid(
    centroid: Offset,
    currentOffset: Offset,
    currentScale: Float,
    canvasWidth: Float,
    canvasHeight: Float
): Offset {
    return Offset(
        x = (centroid.x - canvasWidth / 2f - currentOffset.x) / currentScale,
        y = (centroid.y - canvasHeight / 2f - currentOffset.y) / currentScale
    )
}

private fun calculateZoomOffsetAdjustment(
    transformedCentroid: Offset,
    oldScale: Float,
    newScale: Float
): Offset {
    return Offset(
        x = transformedCentroid.x * (newScale - oldScale),
        y = transformedCentroid.y * (newScale - oldScale)
    )
}

@Composable
fun Modifier.zoomablePannable(
    contentWidth: Dp,
    contentHeight: Dp,
    state: ZoomPanState,
    minZoom: Float = 0.333f,
    maxZoom: Float = 1f
): Modifier {
    val (w, h) = with(LocalConfiguration.current) { screenWidthDp.dp to screenHeightDp.dp }
    val (screenWidthPx, screenHeightPx) = with(LocalDensity.current) { w.toPx() to h.toPx() }
    val density = LocalDensity.current
    val contentWidthPx = with(density) { contentWidth.toPx() }
    val contentHeightPx = with(density) { contentHeight.toPx() } // these will be used to constrain view

    return this
        .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, _ ->
                val oldScale = state.scale
                val newScale = (oldScale * zoom).coerceIn(minZoom, maxZoom)

                val transformedCentroid = calculateZoomCentroid(
                    centroid = centroid,
                    currentOffset = state.offset,
                    currentScale = oldScale,
                    canvasWidth = screenWidthPx,
                    canvasHeight = screenHeightPx
                )
                val zoomAdjustment = calculateZoomOffsetAdjustment(
                    transformedCentroid = transformedCentroid,
                    oldScale = oldScale,
                    newScale = newScale
                )
                val newOffset = state.offset + pan - zoomAdjustment

                state.scale = newScale
                state.offset = newOffset
            }
        }
        .graphicsLayer(
            scaleX = state.scale,
            scaleY = state.scale,
            translationX = state.offset.x,
            translationY = state.offset.y
        )
}