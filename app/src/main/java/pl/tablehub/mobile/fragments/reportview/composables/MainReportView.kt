package pl.tablehub.mobile.fragments.reportview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TableHubTheme

@Composable
fun MainReportView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onRestaurantSelected: (Restaurant) -> Unit = {},
    restaurants: List<Restaurant> = emptyList(),
    userLocation: Location = Location(0.0, 0.0)
) {
    var searchText by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val smallSpacing = (screenHeight * 0.01f).coerceAtLeast(8f).dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
    ) {
        TopBarContent(onBackClick = onBack)

        MainReportTextField(
            onValueChange = { newValue ->
                searchText = newValue
            }
        )

        Spacer(modifier = Modifier.height(smallSpacing))

        RestaurantList(
            searchText = searchText,
            userLocation = userLocation,
            onRestaurantClick = { restaurant ->
                onRestaurantSelected(restaurant)
            },
            restaurants = restaurants
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainReportViewPreview() {
    TableHubTheme {
        MainReportView()
    }
}