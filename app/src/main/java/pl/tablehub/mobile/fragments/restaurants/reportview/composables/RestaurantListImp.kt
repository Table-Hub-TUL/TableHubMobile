package pl.tablehub.mobile.fragments.restaurants.reportview.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import pl.tablehub.mobile.model.v1.Location
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun RestaurantList(
    searchText: String,
    userLocation: Location,
    onRestaurantClick: (RestaurantListItem) -> Unit = {},
    restaurants: List<RestaurantListItem> = emptyList()
) {
    val dims = rememberGlobalDimensions()

    val restaurantsWithDistance = remember(userLocation) {
        restaurants.map { restaurant ->
            val distanceInMeters = calculateDistance(
                userLocation.latitude, userLocation.longitude,
                restaurant.location.latitude, restaurant.location.longitude
            )
            Pair(restaurant, distanceInMeters)
        }.sortedBy { it.second }
    }

    val filteredRestaurants = remember(searchText, restaurantsWithDistance) {
        if (searchText.isEmpty()) {
            restaurantsWithDistance
        } else {
            restaurantsWithDistance.filter {
                it.first.name.contains(searchText, ignoreCase = true) ||
                        it.first.cuisine.any { cuisine ->
                            cuisine.contains(searchText, ignoreCase = true)
                        }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dims.paddingBig)
    ) {
        items(filteredRestaurants) { (restaurant, distance) ->
            RestaurantItem(
                restaurant = restaurant,
                distanceInMeters = distance,
                onClick = { onRestaurantClick(restaurant) }
            )
            Spacer(modifier = Modifier.padding(vertical = dims.paddingSmall/2))
        }
    }
}

@Composable
fun RestaurantItem(
    restaurant: RestaurantListItem,
    distanceInMeters: Int,
    onClick: () -> Unit
) {
    val dims = rememberGlobalDimensions()
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY_COLOR,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dims.paddingSmall/2)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dims.paddingSmall)
        ) {
            Text(
                text = "${distanceInMeters}m",
                color = Color.White,
                fontSize = dims.textSizeSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.width(dims.buttonHeight * 1.2f)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dims.paddingSmall)
            ) {
                Text(
                    text = restaurant.name,
                    color = Color.White,
                    fontSize = dims.textSizeSmall
                )
                Text(
                    text = restaurant.address.toString(),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = dims.textSizeMinimal
                )
                Text(
                    text = restaurant.cuisine.joinToString(", "){ it.lowercase().replaceFirstChar { c -> c.uppercaseChar() } },
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = dims.textSizeMinimal
                )
            }
        }
    }
}

private fun calculateDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Int {
    val r = 6371e3
    val phi1 = lat1 * Math.PI / 180
    val phi2 = lat2 * Math.PI / 180
    val deltaPhi = (lat2 - lat1) * Math.PI / 180
    val deltaLambda = (lon2 - lon1) * Math.PI / 180

    val a = sin(deltaPhi / 2) * sin(deltaPhi / 2) +
            cos(phi1) * cos(phi2) *
            sin(deltaLambda / 2) * sin(deltaLambda / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = r * c

    return distance.roundToInt()
}