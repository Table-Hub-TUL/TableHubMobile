package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.ui.shared.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.constants.VERTICAL_PADDING
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR

@Composable
fun FilterMenu(
    drawerState: DrawerState,
    restaurants: List<RestaurantResponseDTO>,
    tables: HashMap<Long, List<Section>>,
    onFilterResult: (List<RestaurantResponseDTO>) -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
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
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = true, onClick = {})
            )
        }

        AnimatedVisibility(
            visible = isOpen,
            enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
            exit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                Box(
                    modifier = Modifier.fillMaxHeight().width(300.dp)
                        .background(
                            color = SECONDARY_COLOR,
                            shape = RoundedCornerShape(
                                topStart = VERTICAL_PADDING.dp / 2,
                                bottomStart = HORIZONTAL_PADDING.dp / 3
                            )
                        )
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(
                                    horizontal = HORIZONTAL_PADDING.dp / 3,
                                    vertical = VERTICAL_PADDING.dp / 2
                                ),
                            horizontalArrangement = Arrangement.End
                        ) {
                            MainViewButton(
                                text = stringResource(R.string.filter),
                                onClick = { scope.launch { drawerState.close() } },
                                icon = FilterIcon
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        RatingFilter(selectedRating, { selectedRating = it })

                        Spacer(modifier = Modifier.height(VERTICAL_PADDING.dp * 2))

                        CuisineFilter(cuisines, selectedCuisine, { selectedCuisine = it })

                        Spacer(modifier = Modifier.height(VERTICAL_PADDING.dp * 2))

                        TableFilter(minFreeTables, { minFreeTables = it })
                    }
                }
            }
        }
    }
}