package pl.tablehub.mobile.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.services.interfaces.TablesService
import javax.inject.Inject

@HiltViewModel
class MainViewViewModel @Inject constructor(
    private val application: Application,
    private val service: TablesService
) : AndroidViewModel(application) {

    private val _restaurants = MutableStateFlow<List<RestaurantResponseDTO>>(emptyList())
    val restaurants: StateFlow<List<RestaurantResponseDTO>> = _restaurants
    private val _userLocation = MutableStateFlow(Location(0.0, 0.0))
    val userLocation: StateFlow<Location> = _userLocation
    /**
    * Contents of HashMap can be misleading hoverer it is portable way to store all free tables in each restaurant
    * */
    private val _tables: MutableStateFlow<HashMap<Long, List<Section>>> = MutableStateFlow(HashMap())
    val tables: StateFlow<HashMap<Long, List<Section>>> = _tables

    init {
        fetchUserLocation()
        fetchRestaurants()
        fetchTables()
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            val requestParams = RestaurantsRequest(
                localization = _userLocation.value,
                radius = 1000.0,
                filters = emptyList()
            )
            val response = service.requestRestaurants(requestParams)
            _restaurants.value = response.restaurants
        }
    }

    @SuppressLint("MissingPermission")
    // TODO: Add permission check
    private fun fetchUserLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                _userLocation.value = Location(location.longitude, location.latitude)
            } else {
                // Dom Czosnka
                _userLocation.value = Location(19.457216, 51.759445)
            }
        }
    }

    private fun fetchTables() {
        viewModelScope.launch {
            val response = service.subscribeRestaurants(_restaurants.value)
            response.map {
                responseIdx -> tables.value[responseIdx.restaurantId] = responseIdx.initialState.sections
            }
        }
    }
}