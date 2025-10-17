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
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun SectionSelectionButtons(
    restaurant: Restaurant,
    onSectionSelected: (Section) -> Unit
) {
    var selectedSectionId by remember { mutableStateOf<Long>(restaurant.sections?.firstOrNull()?.id ?: -1L) }
    val sections = restaurant.sections
    val dims = rememberGlobalDimensions()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dims.horizontalPadding, vertical = dims.paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(dims.horizontalPadding)
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