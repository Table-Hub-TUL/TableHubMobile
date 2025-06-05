package pl.tablehub.mobile.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
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
import pl.tablehub.mobile.model.Table
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.services.implementation.TablesServiceImplementation
import pl.tablehub.mobile.services.interfaces.TablesService
import pl.tablehub.mobile.services.mock.MockTableService
import java.lang.ref.WeakReference
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

    private var tablesServiceRef: WeakReference<TablesService>? = null
    private var isServiceBound = false
    private val _serviceConnected = MutableStateFlow(false)
    val serviceConnected: StateFlow<Boolean> = _serviceConnected

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TablesServiceImplementation.LocalBinder
            val serviceInstance = binder.getService()
            tablesServiceRef = WeakReference(serviceInstance)
            isServiceBound = true
            _serviceConnected.value = true
            Log.d("MainViewViewModel", "Service connected successfully")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            tablesServiceRef = null
            isServiceBound = false
            _serviceConnected.value = false
            Log.d("MainViewViewModel", "Service disconnected")
        }
    }

    init {
        bindService()
        fetchUserLocation()
    }

    private fun bindService() {
        val serviceIntent: Intent = Intent(application, TablesServiceImplementation::class.java)
        application.startService(serviceIntent)
        application.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
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

    private fun getService(): TablesService? {
        return if (isServiceBound) {
            tablesServiceRef?.get()
        } else {
            null
        }
    }

    fun updateTableStatus(updates: TableUpdateRequest) {
        val tablesService = getService()
        if(isServiceBound && tablesService != null) {
            viewModelScope.launch {
                tablesService.updateTableStatus(updates)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (isServiceBound) {
            application.unbindService(serviceConnection)
            isServiceBound = false
        }
    }
}