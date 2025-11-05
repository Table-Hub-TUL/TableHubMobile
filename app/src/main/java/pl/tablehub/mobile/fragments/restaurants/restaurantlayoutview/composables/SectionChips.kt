package pl.tablehub.mobile.fragments.restaurants.restaurantlayoutview.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.model.v2.Section
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun SectionChips(
    sections: List<Section>,
    selectedSection: Section?,
    onSectionSelected: (Section) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sections.forEach { section ->
            FilterChip(
                selected = section.id == selectedSection?.id,
                onClick = { onSectionSelected(section) },
                label = {
                    Text(
                        text = section.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PRIMARY_COLOR,
                    selectedLabelColor = SECONDARY_COLOR,
                    containerColor = SECONDARY_COLOR,
                    labelColor = TERTIARY_COLOR
                ),
                modifier = Modifier.height(44.dp)
            )
        }
    }
}