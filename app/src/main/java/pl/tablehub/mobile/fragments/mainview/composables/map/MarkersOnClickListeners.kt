package pl.tablehub.mobile.fragments.mainview.composables.map

import android.util.Log
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantListItem

internal class MarkersOnClickListeners(
    private val restaurants: List<RestaurantListItem>,
    private val onMarkerClick: (RestaurantListItem) -> Unit
) : OnPointAnnotationClickListener {
    override fun onAnnotationClick(annotation: PointAnnotation): Boolean {
        val restaurantId = try {
            annotation.getData()?.asLong
        } catch (e: Exception) {
            Log.e("MAP ERROR", "Invalid annotation data")
            return false
        }
        val clickedRestaurant: RestaurantListItem = restaurants.find { restaurant: RestaurantListItem ->
            restaurant.id == restaurantId
        } ?: return false
        onMarkerClick(clickedRestaurant)
        return true
    }
}