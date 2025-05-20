package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun FloorSelectionButtons(
    restaurant: Restaurant,
    onSectionSelected: (Section) -> Unit
) {
    var selectedSectionId by remember { mutableStateOf<Long?>(null) }
    val sections = restaurant.sections

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val topSections = if (sections.size > 3) sections.take(3) else sections

        topSections.forEach { section ->
            FloorButton(
                text = section.name,
                isSelected = selectedSectionId == section.id,
                onClick = {
                    selectedSectionId = section.id
                    onSectionSelected(section)
                },
                modifier = Modifier.weight(1f)
            )
        }

        repeat(3 - topSections.size) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    if (sections.size > 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            FloorButton(
                text = sections[3].name,
                isSelected = selectedSectionId == sections[3].id,
                onClick = {
                    selectedSectionId = sections[3].id
                    onSectionSelected(sections[3])
                },
                modifier = Modifier.weight(0.3f)
            )

            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}

@Composable
fun FloorButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) PRIMARY_COLOR else TERTIARY_COLOR // Orange by default, secondary color when selected

    Row(
        modifier = modifier
            .requiredHeight(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}