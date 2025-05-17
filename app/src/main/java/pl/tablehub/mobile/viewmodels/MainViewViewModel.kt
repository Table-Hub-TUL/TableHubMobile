package pl.tablehub.mobile.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
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
import pl.tablehub.mobile.services.implementation.TablesServiceImplementation
import pl.tablehub.mobile.services.interfaces.TablesService
import pl.tablehub.mobile.services.mock.MockTableService
import javax.inject.Inject

@HiltViewModel
class MainViewViewModel @Inject constructor(
    private val application: Application,
    repository: IRestaurantsRepository
) : AndroidViewModel(application) {
    private val _restaurants = repository.restaurantsMap
    val restaurants: StateFlow<Map<Long, Restaurant>> = _restaurants
    private val _userLocation = MutableStateFlow(Location(0.0, 0.0))
    val userLocation: StateFlow<Location> = _userLocation

    init {
        val serviceIntent: Intent = Intent(application, MockTableService::class.java)
        application.startService(serviceIntent)
        Log.d("CO", "JEST")
        fetchUserLocation()
    }

    fun fetchUserLocation() {
        if (ContextCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
            locationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    _userLocation.value = Location(location.longitude, location.latitude)
                } else {
                    _userLocation.value = Location(0.0, 0.0)
                }
            }
        } else {
            _userLocation.value = Location(0.0, 0.0)
        }
    }
}