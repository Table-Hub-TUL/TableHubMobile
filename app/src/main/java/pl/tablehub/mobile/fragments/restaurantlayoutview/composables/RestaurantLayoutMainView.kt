package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.client.model.TableStatusChange
import pl.tablehub.mobile.fragments.restaurantlayoutview.temp.sampleSections
import pl.tablehub.mobile.model.v2.Section
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
@Preview
fun RestaurantLayoutMainView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onFinishChanges: () -> Unit = {},
    onTableStatusChanged: ((TableStatusChange) -> Unit) = { _: TableStatusChange -> },
    sections: List<Section> = sampleSections,
) {
    val dims = rememberGlobalDimensions()
    var selectedSection by remember { mutableStateOf(sections.firstOrNull()) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TERTIARY_COLOR)
    ) {
        selectedSection?.let {
            RestaurantMapRenderer(
                it,
                onTableStatusChanged = onTableStatusChanged
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.92f)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SECONDARY_COLOR)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TERTIARY_COLOR.copy(alpha = 0.1f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TERTIARY_COLOR,
                        modifier = Modifier.size(24.dp)
                    )
                }
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
                Spacer(modifier = Modifier.size(44.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            CompactLegend()

            Spacer(modifier = Modifier.height(8.dp))

            if (sections.size > 1) {
                SectionChips(
                    sections = sections,
                    selectedSection = selectedSection,
                    onSectionSelected = { selectedSection = it }
                )
            }
        }

        Button(
            onClick = onFinishChanges,
            colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_COLOR),
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
