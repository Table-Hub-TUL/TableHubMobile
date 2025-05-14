package pl.tablehub.mobile.fragments.mainview.composables

import android.util.Log
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO

internal class MarkersOnClickListeners(
    private val restaurants: List<Restaurant>,
    private val onMarkerClick: (Restaurant) -> Unit
) : OnPointAnnotationClickListener {
    override fun onAnnotationClick(annotation: PointAnnotation): Boolean {
        val restaurantId = try {
            annotation.getData()?.asLong
        } catch (e: Exception) {
            Log.e("MAP ERROR", "Invalid annotation data")
            return false
        }
        val clickedRestaurant: Restaurant = restaurants.find { restaurant: Restaurant ->
            restaurant.id == restaurantId
        } ?: return false
        onMarkerClick(clickedRestaurant)
        return true
    }
}