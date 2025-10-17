package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.client.model.TableStatusChange
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun RestaurantLayoutMainView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onFinishChanges: () -> Unit = {},
    onTableStatusChanged: ((TableStatusChange) -> Unit) = { _: TableStatusChange -> },
    restaurant: Restaurant
) {
    val dims = rememberGlobalDimensions()
    var selectedSection by remember { mutableStateOf(restaurant.sections.firstOrNull()) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
    ) {
        TopBarContent(onBackClick = onBack)

        Text(
            text = stringResource(R.string.report_tables),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dims.paddingBig, vertical = dims.paddingSmall),
            fontSize = dims.textSizeMedium * 2,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = TERTIARY_COLOR
        )

        TableLegendItem(4, GREEN_FREE_COLOR, stringResource(R.string.table_number_eq_seats))

        StateLegend()

        SectionSelectionButtons(
            restaurant = restaurant,
            onSectionSelected = { section ->
                selectedSection = section
            }
        )

        TableLayout(
            restaurantID = restaurant.id,
            selectedSection = selectedSection,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dims.paddingBig, vertical = dims.paddingSmall),
            onTableStatusChanged = onTableStatusChanged
        )

        Button(
            onClick = onFinishChanges,
            colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_COLOR),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dims.paddingBig, vertical = dims.paddingLarge)
                .height(dims.buttonHeight)
        ) {
            Text(
                text = stringResource(R.string.finish_changes),
                color = SECONDARY_COLOR,
                fontSize = dims.textSizeBig,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
