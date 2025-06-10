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
import pl.tablehub.mobile.fragments.mainview.composables.buttons.BottomButtons
import pl.tablehub.mobile.fragments.mainview.composables.filter.FilterMenu
import pl.tablehub.mobile.fragments.mainview.composables.map.MapboxMapWrapper
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Restaurant

@Composable
fun MainMapView(
    restaurants: List<Restaurant>,
    userLocation: Location,
    onReportGeneral: () -> Unit = {},
    onReportSpecific: (Restaurant) -> Unit
) {
    val menuDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val filterDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val locationTrigger = remember { MutableSharedFlow<Unit>(extraBufferCapacity = 1) }
    val centerOnPointTrigger = remember { MutableSharedFlow<Point>(extraBufferCapacity = 1) }
    var selectedRestaurant by remember { mutableStateOf<Restaurant?>(null) }
    var visibleRestaurants by remember { mutableStateOf(restaurants) }
    val tables = restaurants.associateBy( { it.id }, { it.sections })


    MainViewMenu(drawerState = menuDrawerState) {
        FilterMenu(
            drawerState = filterDrawerState,
            restaurants = restaurants,
            tables = tables,
            onFilterResult = { filteredList -> visibleRestaurants = filteredList }
        ) {
            LaunchedEffect(userLocation) {
                scope.launch {
                    locationTrigger.emit(Unit)
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                MapboxMapWrapper(
                    locationTrigger = locationTrigger,
                    centerOnPointTrigger = centerOnPointTrigger,
                    restaurants = visibleRestaurants,
                    potentialCenterLocation = userLocation,
                    tables = tables,
                    onMarkerClick = { restaurant ->
                        selectedRestaurant = restaurant
                        scope.launch {
                            val point = Point.fromLngLat(
                                restaurant.location.longitude,
                                restaurant.location.latitude
                            )
                            centerOnPointTrigger.emit(point)
                        }
                    })
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TopBarContent(
                        onMenuClick = {
                            scope.launch {
                                menuDrawerState.open()
                            }
                        },
                        onFilterClick = {
                            scope.launch {
                                filterDrawerState.open()
                            }
                        }
                    )
                    Box(modifier = Modifier.weight(1f))
                    BottomButtons(
                        onReportClick = onReportGeneral,
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
                        onReportTable = onReportSpecific,
                        onMoreDetailsClick = { _ -> Log.d("PLACEHOLDER", "PLACEHOLDER") })
                }
            }
        }
    }
}