package pl.tablehub.mobile.fragments.mainview.composables.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.shared.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.constants.ICON_SIZE
import pl.tablehub.mobile.ui.shared.constants.VERTICAL_PADDING
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun MainViewMenuReturnButton(
    onReturnClick: () -> Unit
){
    val dims = rememberGlobalDimensions()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dims.horizontalPadding, vertical = dims.paddingHuge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onReturnClick,
            modifier = Modifier.background(
                color = SECONDARY_COLOR, shape = RoundedCornerShape(
                    dims.buttonCornerRadius
                )
            ),
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.menu),
                modifier = Modifier.size(dims.iconSize)
            )
        }
    }
}