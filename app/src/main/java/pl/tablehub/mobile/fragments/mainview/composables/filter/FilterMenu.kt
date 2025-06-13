package pl.tablehub.mobile.fragments.mainview.composables.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.TableStatus

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
    var minFreeSeats by remember { mutableIntStateOf(0) }

    val cuisines = restaurants.flatMap { it.cuisine }.distinct().sorted()

    val applyFilters = {
        val filteredList = restaurants.filter { restaurant ->
            restaurant.rating >= selectedRating &&
                    (selectedCuisine == null || restaurant.cuisine.contains(selectedCuisine)) &&
                    (hasTableWithMinimumSeats(restaurant.id, tables, minFreeSeats))
        }
        onFilterResult(filteredList)
    }

    LaunchedEffect(restaurants, selectedRating, selectedCuisine, minFreeSeats) {
        applyFilters()
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
                minFreeSeats = minFreeSeats,
                onMinFreeSeatsChanged = { minFreeSeats = it }
            )
        }
    }
}

private fun hasTableWithMinimumSeats(restaurantId: Long, tables: Map<Long, List<Section>>, minSeats: Int): Boolean {
    val restaurantSections = tables[restaurantId] ?: return false
    restaurantSections.forEach { section ->
        section.tables.forEach { table ->
            if (table.status == TableStatus.AVAILABLE && table.capacity >= minSeats) {
                return true
            } else if (minSeats == 0) {
                return true
            }
        }
    }
    return false
}