package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.ui.shared.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.constants.VERTICAL_PADDING
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

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
    var cuisineDropdownExpanded by remember { mutableStateOf(false) }

    val cuisines = restaurants.flatMap { it.cuisine }.distinct().sorted()

    val filteredRestaurants = restaurants.filter { restaurant ->
        val ratingOk = restaurant.rating >= selectedRating
        val cuisineOk = selectedCuisine == null || restaurant.cuisine.contains(selectedCuisine)
        val freeTables = calculateFreeTablesText(restaurant.id, tables).toIntOrNull() ?: 0
        val tablesOk = freeTables >= minFreeTables
        ratingOk && cuisineOk && tablesOk
    }

    LaunchedEffect(selectedRating, selectedCuisine, minFreeTables) {
        onFilterResult(filteredRestaurants)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = true, onClick = {})
            )
        }

        AnimatedVisibility(
            visible = isOpen,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp)
                        .background(
                            color = SECONDARY_COLOR,
                            shape = RoundedCornerShape(topStart = VERTICAL_PADDING.dp / 2, bottomStart = HORIZONTAL_PADDING.dp / 3)
                        )
                        .padding(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = HORIZONTAL_PADDING.dp / 3, vertical = VERTICAL_PADDING.dp / 2),
                            horizontalArrangement = Arrangement.End
                        ) {
                            MainViewButton(
                                text = stringResource(R.string.filter),
                                onClick = {
                                    scope.launch { drawerState.close() }
                                },
                                icon = FilterIcon
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(stringResource(R.string.minimum_rating)+": ${"%.1f".format(selectedRating)}",
                            color = TERTIARY_COLOR,
                            fontSize = 16.sp
                        )
                        Slider(
                            value = selectedRating.toFloat(),
                            onValueChange = { selectedRating = it.toDouble() },
                            valueRange = 0f..5f,
                            steps = 4,
                            modifier = Modifier.padding(vertical = 8.dp),
                            colors = SliderDefaults.colors(
                                activeTrackColor = PRIMARY_COLOR,
                                inactiveTrackColor = Color.LightGray,
                                thumbColor = PRIMARY_COLOR
                            )
                        )

                        Spacer(modifier = Modifier.height(VERTICAL_PADDING.dp * 2))

                        Text(stringResource(R.string.cuisines),
                            color = TERTIARY_COLOR,
                            fontSize = 16.sp
                        )
                        var buttonWidth by remember { mutableStateOf(0) }
                        Box {
                            OutlinedButton(onClick = { cuisineDropdownExpanded = true },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = PRIMARY_COLOR,
                                    contentColor = PRIMARY_COLOR
                                ),
                                modifier = Modifier.fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        buttonWidth = coordinates.size.width
                                    },
                                contentPadding = PaddingValues(vertical = 12.dp)
                            ) {
                                Text(selectedCuisine ?: stringResource(R.string.any),
                                    color = SECONDARY_COLOR,
                                    fontSize = 16.sp
                                )
                            }
                            DropdownMenu(
                                shape = RoundedCornerShape(12.dp),
                                expanded = cuisineDropdownExpanded,
                                onDismissRequest = { cuisineDropdownExpanded = false },
                                modifier = Modifier.background(PRIMARY_COLOR)
                                    .width(with(LocalDensity.current) { buttonWidth.toDp() })
                            ) {
                                DropdownMenuItem(
                                    text = { Text(stringResource(R.string.any),
                                        color = SECONDARY_COLOR,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    ) },
                                    onClick = {
                                        selectedCuisine = null
                                        cuisineDropdownExpanded = false
                                    }
                                )
                                cuisines.forEach { cuisine ->
                                    DropdownMenuItem(
                                        text = { Text(cuisine,
                                            color = SECONDARY_COLOR,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        ) },
                                        onClick = {
                                            selectedCuisine = cuisine
                                            cuisineDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(VERTICAL_PADDING.dp * 2))

                        OutlinedTextField(
                            value = minFreeTables.toString(),
                            onValueChange = {
                                minFreeTables = it.toIntOrNull() ?: 0
                            },
                            label = {
                                Text(
                                    text = stringResource(R.string.minimum_free_tables),
                                    fontSize = 16.sp,
                                    color = TERTIARY_COLOR
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            visualTransformation = VisualTransformation.None,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PRIMARY_COLOR,
                                unfocusedBorderColor = TERTIARY_COLOR
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                    }
                }
            }
        }
    }
}
