package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MapboxMapWrapper(locationTrigger: SharedFlow<Unit>) {
    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(18.0)
            center(Point.fromLngLat(19.578037, 51.78987))
            pitch(0.0)
            bearing(0.0)
        }
    }
    MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
        compass = {
            Compass(
                alignment = Alignment.BottomEnd,
                modifier = Modifier.padding(start = 0.dp, bottom = 100.dp, end = 8.dp)
            )
        }
    ) {
        MapEffect(Unit) { mapView ->
            mapView.location.updateSettings {
                locationPuck = createDefault2DPuck(withBearing = true)
                enabled = true
                puckBearing = PuckBearing.COURSE
                puckBearingEnabled = true
            }
        }
    }

    LaunchedEffect(locationTrigger, mapViewportState) {
        locationTrigger.collectLatest {
            mapViewportState.transitionToFollowPuckState()
        }
    }
}