package pl.tablehub.mobile.fragments.mainview.composables

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

@Composable
fun FilterDrawer(
    drawerState: DrawerState,
    selectedRating: Double,
    onRatingChanged: (Double) -> Unit,
    cuisines: List<String>,
    selectedCuisine: String?,
    onCuisineSelected: (String?) -> Unit,
    minFreeTables: Int,
    onMinFreeTablesChanged: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
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
                FilterTopContent(drawerState)

                Spacer(modifier = Modifier.height(16.dp))

                RatingFilter(selectedRating, onRatingChanged)

                Spacer(modifier = Modifier.height(VERTICAL_PADDING.dp * 2))

                CuisineFilter(cuisines, selectedCuisine, onCuisineSelected)

                Spacer(modifier = Modifier.height(VERTICAL_PADDING.dp * 2))

                TableFilter(minFreeTables, onMinFreeTablesChanged)
            }
        }
    }
}