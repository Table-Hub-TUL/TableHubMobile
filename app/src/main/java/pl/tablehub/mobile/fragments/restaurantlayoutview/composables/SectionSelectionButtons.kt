package pl.tablehub.mobile.fragments.restaurantlayoutview.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section

@Composable
fun SectionSelectionButtons(
    restaurant: Restaurant,
    onSectionSelected: (Section) -> Unit
) {
    var selectedSectionId by remember { mutableStateOf<Long?>(null) }
    val sections = restaurant.sections

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val horizontalPadding = (screenWidth * 0.06f).coerceAtLeast(16f).coerceAtMost(24f).dp
    val smallSpacing = (screenHeight * 0.01f).coerceAtLeast(8f).dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = smallSpacing),
        horizontalArrangement = Arrangement.spacedBy(horizontalPadding)
    ) {
        sections.forEach { section ->
            SectionButton(
                text = section.name,
                isSelected = selectedSectionId == section.id,
                onClick = {
                    selectedSectionId = section.id
                    onSectionSelected(section)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}