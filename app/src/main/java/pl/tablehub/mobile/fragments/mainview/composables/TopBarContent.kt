package pl.tablehub.mobile.fragments.mainview.composables

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
import pl.tablehub.mobile.ui.shared.composables.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.shared.composables.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.composables.constants.ICON_SIZE
import pl.tablehub.mobile.ui.shared.composables.constants.VERTICAL_PADDING
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR

@Composable
fun TopBarContent(
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = HORIZONTAL_PADDING.dp, vertical = VERTICAL_PADDING.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.background(color = SECONDARY_COLOR, shape = RoundedCornerShape(
                CORNER_ROUND_SIZE.dp)),
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.menu),
                modifier = Modifier.size(ICON_SIZE.dp)
            )
        }
        MainViewButton(
            text = stringResource(R.string.filter),
            onClick = onFilterClick,
            icon = FilterIcon
        )
    }
} 