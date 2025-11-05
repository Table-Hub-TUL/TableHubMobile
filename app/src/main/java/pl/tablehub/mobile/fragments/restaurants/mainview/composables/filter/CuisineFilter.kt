package pl.tablehub.mobile.fragments.restaurants.mainview.composables.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun CuisineFilter(
    cuisines: List<String>,
    selectedCuisine: String?,
    onCuisineSelected: (String?) -> Unit
) {
    val dims = rememberGlobalDimensions()
    var cuisineDropdownExpanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableIntStateOf(0) }

    Text(
        text = stringResource(R.string.cuisines),
        color = TERTIARY_COLOR,
        fontSize = dims.textSizeSmall
    )

    Box {
        OutlinedButton(
            onClick = { cuisineDropdownExpanded = true },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = PRIMARY_COLOR,
                contentColor = PRIMARY_COLOR
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    buttonWidth = coordinates.size.width
                },
            contentPadding = PaddingValues(vertical = dims.paddingLarge)
        ) {
            Text(
                text = selectedCuisine ?: stringResource(R.string.any),
                color = SECONDARY_COLOR,
                fontSize = dims.textSizeSmall
            )
        }

        DropdownMenu(
            shape = RoundedCornerShape(dims.buttonCornerRadius),
            expanded = cuisineDropdownExpanded,
            onDismissRequest = { cuisineDropdownExpanded = false },
            modifier = Modifier
                .background(PRIMARY_COLOR)
                .width(with(LocalDensity.current) { buttonWidth.toDp() })
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.any),
                        color = SECONDARY_COLOR,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                onClick = {
                    onCuisineSelected(null)
                    cuisineDropdownExpanded = false
                }
            )

            cuisines.forEach { cuisine ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = cuisine.lowercase().replaceFirstChar { it.uppercaseChar() },
                            color = SECONDARY_COLOR,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    onClick = {
                        onCuisineSelected(cuisine)
                        cuisineDropdownExpanded = false
                    }
                )
            }
        }
    }
}