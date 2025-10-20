package pl.tablehub.mobile.viewmodels

import android.Manifest
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.model.v1.Location
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.client.model.TableStatusChange
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.services.TablesService
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

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TablesService.LocalBinder
            val serviceInstance = binder.getService()
            tablesServiceRef = WeakReference(serviceInstance)
            isServiceBound = true
            _serviceConnected.value = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            tablesServiceRef = null
            isServiceBound = false
            _serviceConnected.value = false
        }
    }

    init {
        bindService()
        fetchUserLocation()
    }

    private fun bindService() {
        val serviceIntent = Intent(application, TablesService::class.java)
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

    fun updateTableStatus(update: TableStatusChange) {
        val tablesService = getService()
        if(isServiceBound && tablesService != null) {
            viewModelScope.launch {
                tablesService.updateTableStatus(update)
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