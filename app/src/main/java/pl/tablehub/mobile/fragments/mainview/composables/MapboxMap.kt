package pl.tablehub.mobile.fragments.mainview.composables

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.Restaurant

@Composable
fun MapboxMapWrapper(
    locationTrigger: SharedFlow<Unit>,
    restaurants: List<Restaurant>
) {
    val context = LocalContext.current
    val markerBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.logo_for_system)
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(14.0)
            center(Point.fromLngLat(19.457216, 51.759445))
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
        MapEffect(restaurants, markerBitmap) { mapView ->
            val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
            pointAnnotationManager.deleteAll()
            val pointAnnotationOptionsList = restaurants.map {
                PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(it.location.longitude, it.location.latitude))
                    .withIconImage(markerBitmap)
                    .withIconSize(0.1)
            }
            pointAnnotationManager.create(pointAnnotationOptionsList)
        }
    }
    LaunchedEffect(locationTrigger, mapViewportState) {
        locationTrigger.collectLatest {
            mapViewportState.transitionToFollowPuckState()
        }
    }
}