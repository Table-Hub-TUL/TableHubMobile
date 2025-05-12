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
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.services.interfaces.TablesService
import javax.inject.Inject

@HiltViewModel
class MainViewViewModel @Inject constructor(
    private val application: Application,
    private val service: TablesService,
    private val repository: IRestaurantsRepository
) : AndroidViewModel(application) {
    private val _restaurants = MutableStateFlow(repository.restaurantsMap.value)
    val restaurants: StateFlow<Map<Long, Restaurant>> = _restaurants
    private val _userLocation = MutableStateFlow(Location(0.0, 0.0))
    val userLocation: StateFlow<Location> = _userLocation

    init {
        //TODO START SERVICE
        //SERWIS POWINIEN DZIAŁAĆ W TLE I POWINNO BYĆ WSZYSTKO CACY
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
}