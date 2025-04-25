package pl.tablehub.mobile.fragments.reportview.composables

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
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR

private val DEFAULT_USER_LOCATION = Location(51.7592, 19.4560)

@Composable
fun RestaurantList(
    searchText: String,
    userLocation: Location = DEFAULT_USER_LOCATION,
    onRestaurantClick: (Restaurant) -> Unit = {}
) {
    val allRestaurants = remember {
        listOf(
            Restaurant(
                id = 1,
                name = "Manekin",
                address = "ul. Piotrkowska 67",
                location = Location(51.7686, 19.4573),
                cuisine = listOf("Polish", "European", "Pancakes"),
                rating = 4.7
            ),
            Restaurant(
                id = 2,
                name = "Anatewka",
                address = "ul. 6 Sierpnia 2/4",
                location = Location(51.7704, 19.4549),
                cuisine = listOf("Jewish", "Polish", "European"),
                rating = 4.6
            ),
            Restaurant(
                id = 3,
                name = "Piwnica Łódzka",
                address = "ul. Tymienieckiego 22/24",
                location = Location(51.7471, 19.4662),
                cuisine = listOf("Polish", "Bar", "Pub"),
                rating = 4.3
            ),
            Restaurant(
                id = 4,
                name = "Zielona Restaurant",
                address = "ul. Legionów 2",
                location = Location(51.7784, 19.4467),
                cuisine = listOf("Italian", "Mediterranean"),
                rating = 4.5
            ),
            Restaurant(
                id = 5,
                name = "Sushi Kushi",
                address = "ul. Piotrkowska 123",
                location = Location(51.7623, 19.4571),
                cuisine = listOf("Japanese", "Sushi", "Asian"),
                rating = 4.4
            ),
            Restaurant(
                id = 6,
                name = "Browar Księży Młyn",
                address = "ul. Tymienieckiego 22/24",
                location = Location(51.7476, 19.4659),
                cuisine = listOf("Brewery", "Polish", "Pub"),
                rating = 4.2
            ),
            Restaurant(
                id = 7,
                name = "Viva La Pizza",
                address = "ul. Narutowicza 48",
                location = Location(51.7690, 19.4793),
                cuisine = listOf("Italian", "Pizza"),
                rating = 4.3
            ),
            Restaurant(
                id = 8,
                name = "Bułka z Masłem",
                address = "ul. Piotrkowska 152",
                location = Location(51.7600, 19.4567),
                cuisine = listOf("Breakfast", "Brunch", "Café"),
                rating = 4.1
            )
        )
    }

    val restaurantsWithDistance = remember(userLocation) {
        allRestaurants.map { restaurant ->
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
            .padding(horizontal = 16.dp)
    ) {
        items(filteredRestaurants) { (restaurant, distance) ->
            RestaurantItem(
                restaurant = restaurant,
                distanceInMeters = distance,
                onClick = { onRestaurantClick(restaurant) }
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    distanceInMeters: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY_COLOR,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "${distanceInMeters}m",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.width(60.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = restaurant.name,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = restaurant.address,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = restaurant.cuisine.joinToString(", "),
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp
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