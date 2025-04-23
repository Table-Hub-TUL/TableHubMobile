package pl.tablehub.mobile.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.services.interfaces.TablesService
import javax.inject.Inject

@HiltViewModel
class MainViewViewModel @Inject constructor(
    application: Application,
    private val service: TablesService
) : AndroidViewModel(application) {

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    init {
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            val requestParams = RestaurantsRequest(
                localization = Location(51.759445, 19.457216),
                radius = 1000.0,
                filters = emptyList()
            )
            val response = service.requestRestaurants(requestParams)
            _restaurants.value = response.restaurants
        }
    }
}