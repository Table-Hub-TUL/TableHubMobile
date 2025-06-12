package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.mainview.composables.buttons.MainViewMenuButton
import pl.tablehub.mobile.fragments.mainview.composables.buttons.MainViewMenuReturnButton
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR

@Composable
fun MainViewMenu(
    drawerState: DrawerState,
    onProfileClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        modifier = Modifier.background(SECONDARY_COLOR),
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(SECONDARY_COLOR)) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            MainViewMenuReturnButton(onReturnClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                            MainViewMenuButton(
                                onClick = onProfileClick,
                                imgName = R.drawable.profile,
                                stringFromRes = R.string.profile
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            MainViewMenuButton(
                                onClick = onHistoryClick,
                                imgName = R.drawable.history,
                                stringFromRes = R.string.history
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            MainViewMenuButton(
                                onClick = onSettingsClick,
                                imgName = R.drawable.settings,
                                stringFromRes = R.string.settings
                            )
                        }
                        Column(modifier = Modifier.padding(bottom = 16.dp)) {
                            MainViewMenuButton(
                                onClick = onLogoutClick,
                                imgName = R.drawable.marker,
                                stringFromRes = R.string.logout
                            )
                        }
                    }
                }
            }
        }
    ) {
        content()
    }
}