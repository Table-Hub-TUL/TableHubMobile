import android.util.Log
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlin.math.max
import androidx.compose.ui.graphics.TransformOrigin

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
    canvasWidth: Float,
    canvasHeight: Float,
    currentScale: Float

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

private val HEADER_HEIGHT_DP: Dp = 250.dp

@Composable
fun Modifier.zoomablePannable(
    contentWidth: Dp,
    contentHeight: Dp,
    state: ZoomPanState,
    minZoom: Float = 0.333f,
    maxZoom: Float = 1f
): Modifier {

    val density = LocalDensity.current
    val contentWidthPx = contentWidth.value
    val contentHeightPx = contentHeight.value

    val headerHeightPx: Float = with(LocalDensity.current) { HEADER_HEIGHT_DP.toPx() }

    val screenWidthDp = androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx: Float = with(density) { screenWidthDp.toPx() }
    val scaleToFitWidth: Float = (screenWidthPx / contentWidthPx).coerceAtMost(1f)

    val effectiveMinZoom = max(minZoom, scaleToFitWidth)

    return this
        .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, _ ->

                val screenWidthPx = size.width.toFloat()
                val screenHeightPx = size.height.toFloat()

                val containerWidthPx = size.width.toFloat()
                val containerHeightPx = size.height.toFloat()

                val oldScale = state.scale
                val newScale = (oldScale * zoom).coerceIn(effectiveMinZoom, maxZoom)

                val transformedCentroid = calculateZoomCentroid(
                    centroid = centroid,
                    currentOffset = state.offset,
                    canvasWidth = screenWidthPx,
                    canvasHeight = screenHeightPx,
                    currentScale = oldScale )
                val zoomAdjustment = calculateZoomOffsetAdjustment(
                    transformedCentroid = transformedCentroid,
                    oldScale = oldScale,
                    newScale = newScale
                )
                var newOffset = state.offset + pan - zoomAdjustment

                val scaledContentWidth = contentWidthPx * newScale
                val scaledContentHeight = contentHeightPx * newScale

                val maxNegativeX = max(0f, scaledContentWidth - containerWidthPx)
                val maxNegativeY = max(0f, scaledContentHeight - containerHeightPx)


                val maxPositiveX: Float
                val maxPositiveY: Float

                if (scaledContentWidth <= containerWidthPx) {
                     maxPositiveX = (containerWidthPx - scaledContentWidth) / 2f
                    newOffset = newOffset.copy(x = maxPositiveX)
                } else {
                     maxPositiveX = 0f
                }

                if (scaledContentHeight <= containerHeightPx) {

                    maxPositiveY = headerHeightPx

                } else {
                    maxPositiveY = headerHeightPx
                }


                val finalOffset = Offset(
                    x = newOffset.x.coerceIn(-maxNegativeX, maxPositiveX),
                    y = newOffset.y.coerceIn(-maxNegativeY, maxPositiveY)
                )

                state.scale = newScale
                state.offset = finalOffset
            }
        }
        .graphicsLayer(
            scaleX = state.scale,
            scaleY = state.scale,
            translationX = state.offset.x,
            translationY = state.offset.y,
            transformOrigin = TransformOrigin(0f, 0f)
        )
}