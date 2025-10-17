package pl.tablehub.mobile.fragments.mainview.composables.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.ui.shared.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.constants.VERTICAL_PADDING
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun FilterDrawer(
    drawerState: DrawerState,
    selectedRating: Double,
    onRatingChanged: (Double) -> Unit,
    cuisines: List<String>,
    selectedCuisine: String?,
    onCuisineSelected: (String?) -> Unit,
    minFreeSeats: Int,
    onMinFreeSeatsChanged: (Int) -> Unit
) {
    val dims = rememberGlobalDimensions()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(dims.logoSize.dp * 1.5f)
                .background(
                    color = SECONDARY_COLOR,
                    shape = RoundedCornerShape(
                        topStart = dims.buttonCornerRadius,
                        bottomStart = dims.buttonCornerRadius
                    )
                )
                .padding(dims.paddingLarge)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                FilterTopContent(drawerState)

                Spacer(modifier = Modifier.height(dims.mediumSpacing))

                RatingFilter(selectedRating, onRatingChanged)

                Spacer(modifier = Modifier.height(dims.paddingLarge * 2))

                CuisineFilter(cuisines, selectedCuisine, onCuisineSelected)

                Spacer(modifier = Modifier.height(dims.paddingLarge * 2))

                TableFilterSlider(minFreeSeats, onMinFreeSeatsChanged)
            }
        }
    }
}