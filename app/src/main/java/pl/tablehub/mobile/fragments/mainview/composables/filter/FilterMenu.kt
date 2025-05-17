package pl.tablehub.mobile.fragments.mainview.composables.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import pl.tablehub.mobile.fragments.mainview.composables.map.calculateFreeTablesText
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section

@Composable
fun FilterMenu(
    drawerState: DrawerState,
    restaurants: List<Restaurant>,
    tables: Map<Long, List<Section>>,
    onFilterResult: (List<Restaurant>) -> Unit,
    content: @Composable () -> Unit
) {
    val isOpen = drawerState.isOpen

    var selectedRating by remember { mutableDoubleStateOf(0.0) }
    var selectedCuisine by remember { mutableStateOf<String?>(null) }
    var minFreeTables by remember { mutableIntStateOf(0) }

    val cuisines = restaurants.flatMap { it.cuisine }.distinct().sorted()

    val applyFilters = {
        val filteredList = restaurants.filter { restaurant ->
            restaurant.rating >= selectedRating &&
                    (selectedCuisine == null || restaurant.cuisine.contains(selectedCuisine)) &&
                    (calculateFreeTablesText(restaurant.id, tables).toIntOrNull() ?: 0) >= minFreeTables
        }
        onFilterResult(filteredList)
    }

    LaunchedEffect(selectedRating, selectedCuisine, minFreeTables) {
        applyFilters()
    }

    LaunchedEffect(Unit) {
        onFilterResult(restaurants)
    }

    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.currentValue == DrawerValue.Closed) {
            applyFilters()
        }
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