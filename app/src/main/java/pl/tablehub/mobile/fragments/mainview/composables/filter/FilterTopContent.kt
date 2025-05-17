package pl.tablehub.mobile.fragments.mainview.composables.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.mainview.composables.buttons.MainViewButton
import pl.tablehub.mobile.ui.shared.constants.HORIZONTAL_PADDING
import pl.tablehub.mobile.ui.shared.constants.VERTICAL_PADDING


@Composable
fun FilterTopContent(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = HORIZONTAL_PADDING.dp / 3,
                vertical = VERTICAL_PADDING.dp / 2
            ),
        horizontalArrangement = Arrangement.End
    ) {
        MainViewButton(
            text = stringResource(R.string.filter),
            onClick = { scope.launch { drawerState.close() } },
            icon = FilterIcon
        )
    }
}