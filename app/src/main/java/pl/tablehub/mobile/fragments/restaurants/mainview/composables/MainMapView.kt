package pl.tablehub.mobile.fragments.restaurants.mainview.composables

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.fragments.mainview.composables.buttons.BottomButtons
import pl.tablehub.mobile.fragments.mainview.composables.filter.FilterMenu
import pl.tablehub.mobile.fragments.mainview.composables.map.MapboxMapWrapper
import pl.tablehub.mobile.model.v1.Location
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v1.Table
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.model.v2.TableListItem

@Composable
fun MainMapView(
    restaurants: List<RestaurantListItem>,
    userLocation: Location,
    onReportGeneral: () -> Unit = {},
    onReportSpecific: (RestaurantListItem) -> Unit,
    onMoreDetails: (RestaurantListItem) -> Unit,
    menuOnClicks: Map<String, () -> Unit> = emptyMap()
) {
    val menuDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val filterDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val locationTrigger = remember { MutableSharedFlow<Unit>(extraBufferCapacity = 1) }
    val centerOnPointTrigger = remember { MutableSharedFlow<Point>(extraBufferCapacity = 1) }
    var selectedRestaurant by remember { mutableStateOf<RestaurantListItem?>(null) }
    var visibleRestaurants by remember { mutableStateOf(restaurants) }
    var firstLaunch by rememberSaveable { mutableStateOf(true) }

    MainViewMenu(
        drawerState = menuDrawerState,
        onLogoutClick = menuOnClicks["LOGOUT"] ?: {},
        onSettingsClick = menuOnClicks["SETTINGS"] ?: {}
    ) {
        FilterMenu(
            drawerState = filterDrawerState,
            restaurants = restaurants,
            tables = restaurants.associate {
                it.id to (it.tables?: emptyList<TableListItem>())
            },
            onFilterResult = { filteredList -> visibleRestaurants = filteredList }
        ) {
            val (lng, lat) = userLocation
            LaunchedEffect(userLocation) {
                if(firstLaunch && lng != 0.0 && lat != 0.0) {
                    scope.launch {
                        locationTrigger.emit(Unit)
                        firstLaunch = false
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                MapboxMapWrapper(
                    locationTrigger = locationTrigger,
                    centerOnPointTrigger = centerOnPointTrigger,
                    restaurants = visibleRestaurants,
                    potentialCenterLocation = userLocation,
                    tables = restaurants.associate { restaurant ->
                        restaurant.id to (restaurant.tables?: emptyList<TableListItem>())
                    },
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
                        tables = restaurant.tables?: emptyList<TableListItem>(),
                        onReportTable = onReportSpecific,
                        onMoreDetailsClick = onMoreDetails)
                }
            }
        }
    }
}