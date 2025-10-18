package pl.tablehub.mobile.fragments.mainview.composables.map

import android.annotation.SuppressLint
import android.graphics.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.gson.JsonPrimitive
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.v1.Location
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v1.Section
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions


@SuppressLint("MissingPermission")
@Composable
fun MapboxMapWrapper(
    locationTrigger: SharedFlow<Unit>,
    centerOnPointTrigger: SharedFlow<Point>,
    potentialCenterLocation: Location,
    restaurants: List<Restaurant>,
    tables: Map<Long, List<Section>>,
    onMarkerClick: (Restaurant) -> Unit = {}
) {
    val dims = rememberGlobalDimensions()
    val context = LocalContext.current
    val baseMarkerBitmap = remember {
        BitmapFactory.decodeResource(context.resources, R.drawable.marker)
    }
    val textBitmaps = restaurants.map { restaurant ->
        rememberTextOnBitmap(baseBitmap = baseMarkerBitmap, text = calculateFreeTablesText(restaurant.id, tables))
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(Point.fromLngLat(potentialCenterLocation.longitude, potentialCenterLocation.latitude))
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
                modifier = Modifier.padding(start = 0.dp, bottom = dims.buttonHeight * 2, end = dims.paddingSmall)
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
        var pointAnnotationManager by remember { mutableStateOf<PointAnnotationManager?>(null) }
        MapEffect(textBitmaps) { mapView ->
            if (pointAnnotationManager == null) {
                pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
            }
            pointAnnotationManager!!.deleteAll()
            val pointAnnotationOptionsList = restaurants.zip(textBitmaps).map { (restaurant, textBitmap) ->
                PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(restaurant.location.longitude, restaurant.location.latitude))
                    .withIconImage(textBitmap)
                    .withIconAnchor(IconAnchor.BOTTOM)
                    .withIconSize(0.1)
                    .withData(JsonPrimitive(restaurant.id))
            }
            pointAnnotationManager!!.create(pointAnnotationOptionsList)
            pointAnnotationManager!!.removeClickListener { true }
            pointAnnotationManager!!.addClickListener( MarkersOnClickListeners(restaurants, onMarkerClick = onMarkerClick))
        }

        LaunchedEffect(centerOnPointTrigger, mapViewportState) {
            centerOnPointTrigger.collectLatest { pointToCenterOn ->
                mapViewportState.easeTo(
                    CameraOptions.Builder()
                        .center(pointToCenterOn)
                        .zoom(14.0)
                        .pitch(0.0)
                        .bearing(0.0)
                        .build(),
                    MapAnimationOptions.mapAnimationOptions { duration(2000L) }
                )
            }
        }

        LaunchedEffect(locationTrigger, mapViewportState) {
            locationTrigger.collectLatest {
                val followOptions = FollowPuckViewportStateOptions.Builder()
                    .pitch(0.0)
                    .bearing(FollowPuckViewportStateBearing.Constant(0.0))
                    .build()
                mapViewportState.transitionToFollowPuckState(followOptions)
            }
        }
    }
}