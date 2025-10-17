package pl.tablehub.mobile.fragments.mainview.composables.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.shared.constants.BUTTON_HEIGHT
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.shared.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.constants.ICON_SIZE
import pl.tablehub.mobile.ui.shared.constants.VERTICAL_PADDING
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun BottomButtons(
    onReportClick: () -> Unit,
    onLocationClick: () -> Unit
) {
    val dims = rememberGlobalDimensions()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dims.horizontalPadding, vertical = dims.paddingHuge),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        MainViewButton(
            text = stringResource(R.string.report_free_tables),
            onClick = onReportClick
        )
        Spacer(modifier = Modifier.weight(0.5f))
        IconButton(onClick = onLocationClick,
            modifier = Modifier
                .size(dims.buttonHeight)
                .background(
                    color = SECONDARY_COLOR,
                    shape = RoundedCornerShape(dims.buttonCornerRadius))
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = stringResource(R.string.current_location),
                tint = PRIMARY_COLOR,
                modifier = Modifier.size(dims.iconSize)
            )
        }
    }
} 