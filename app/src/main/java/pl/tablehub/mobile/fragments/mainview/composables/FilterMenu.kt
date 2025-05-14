package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO

@Composable
fun FilterMenu(
    drawerState: DrawerState,
    restaurants: List<RestaurantResponseDTO>,
    tables: HashMap<Long, List<Section>>,
    onFilterResult: (List<RestaurantResponseDTO>) -> Unit,
    content: @Composable () -> Unit
) {
    val isOpen = drawerState.isOpen

    var selectedRating by remember { mutableStateOf(0.0) }
    var selectedCuisine by remember { mutableStateOf<String?>(null) }
    var minFreeTables by remember { mutableStateOf(0) }

    val cuisines = restaurants.flatMap { it.cuisine }.distinct().sorted()

    LaunchedEffect(selectedRating, selectedCuisine, minFreeTables) {
        onFilterResult(restaurants.filter { restaurant ->
            restaurant.rating >= selectedRating &&
                    (selectedCuisine == null || restaurant.cuisine.contains(selectedCuisine)) &&
                    (calculateFreeTablesText(restaurant.id, tables).toIntOrNull() ?: 0) >= minFreeTables
        })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (isOpen) {
            FilterOverlay()
        }

        AnimatedVisibility(
            visible = isOpen,
            enter = FilterAnimations.enterTransition(),
            exit = FilterAnimations.exitTransition()
        ) {
            FilterDrawer(
                drawerState = drawerState,
                selectedRating = selectedRating,
                onRatingChanged = { selectedRating = it },
                cuisines = cuisines,
                selectedCuisine = selectedCuisine,
                onCuisineSelected = { selectedCuisine = it },
                minFreeTables = minFreeTables,
                onMinFreeTablesChanged = { minFreeTables = it }
            )
        }
    }
}