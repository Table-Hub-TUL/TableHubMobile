package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@Composable
fun MapboxMapWrapper() {
    MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = rememberMapViewportState {
            setCameraOptions {
                zoom(18.0)
                center(Point.fromLngLat(19.578037, 51.78987))
                pitch(0.0)
                bearing(0.0)
            }
        },
        compass = {
            Compass(
                alignment = Alignment.BottomEnd,
                modifier = Modifier.padding(start = 0.dp, bottom = 100.dp, end = 8.dp)
            )
        }
    )
}