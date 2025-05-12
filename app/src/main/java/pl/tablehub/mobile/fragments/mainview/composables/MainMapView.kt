package pl.tablehub.mobile.fragments.mainview.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.Section

@Composable
fun MainMapView(
    restaurants: List<Restaurant>,
    userLocation: Location,
    tables: HashMap<Long, List<Section>>,
    onReport: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val locationTrigger = remember { MutableSharedFlow<Unit>(extraBufferCapacity = 1) }
    val centerOnPointTrigger = remember { MutableSharedFlow<Point>(extraBufferCapacity = 1) }
    var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }

    MainViewMenu(drawerState = drawerState) {
        LaunchedEffect(Unit) {
            scope.launch {
                locationTrigger.emit(Unit)
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            MapboxMapWrapper(
                locationTrigger = locationTrigger,
                centerOnPointTrigger = centerOnPointTrigger,
                restaurants = restaurants,
                potentialCenterLocation = userLocation,
                tables = tables,
                onMarkerClick = { restaurant ->
                    selectedRestaurant = restaurant
                    scope.launch {
                        val point = Point.fromLngLat(restaurant.location.longitude, restaurant.location.latitude)
                        centerOnPointTrigger.emit(point)
                    }
                })
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBarContent(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onFilterClick = { /* TODO: Implement filter action */ }
                )
                Box(modifier = Modifier.weight(1f))
                BottomButtons(
                    onReportClick = onReport,
                    onLocationClick = {
                        scope.launch {
                            locationTrigger.tryEmit(Unit)
                        }
                    }
                )
            }
            selectedRestaurant?.let { restaurant ->
                RestaurantDetailsPopup(
                    restaurant = restaurant,
                    onDismissRequest = { selectedRestaurant = null },
                    sections = tables[restaurant.id] ?: emptyList(),
                    onMoreDetailsClick = {_ -> Log.d("PLACEHOLDER", "PLACEHOLDER")})
            }
        }
    }
}