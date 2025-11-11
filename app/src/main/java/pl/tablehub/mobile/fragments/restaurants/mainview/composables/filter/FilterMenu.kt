package pl.tablehub.mobile.fragments.restaurants.mainview.composables.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import pl.tablehub.mobile.client.model.restaurants.RestaurantSearchQuery
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v1.Section
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.model.v2.TableListItem

@Composable
fun FilterMenu(
    drawerState: DrawerState,
    filters: RestaurantSearchQuery,
    cuisines: List<String>,
    onRatingChanged: (Double) -> Unit,
    onCuisineSelected: (String?) -> Unit,
    onMinFreeSeatsChanged: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    val isOpen = drawerState.isOpen
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = isOpen,
            enter = FilterAnimations.enterTransition(),
            exit = FilterAnimations.exitTransition()
        ) {
            if (isOpen) {
                FilterOverlay {
                    scope.launch {
                        drawerState.close()
                    }
                }
            }
            FilterDrawer(
                drawerState = drawerState,
                selectedRating = filters.rating,
                onRatingChanged = onRatingChanged,
                cuisines = cuisines,
                selectedCuisine = filters.cuisine.firstOrNull(),
                onCuisineSelected = onCuisineSelected,
                minFreeSeats = filters.minFreeSeats ?: 0,
                onMinFreeSeatsChanged = onMinFreeSeatsChanged
            )
        }
    }
}