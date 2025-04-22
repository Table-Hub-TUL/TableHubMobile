package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainViewMenu(
    drawerState: DrawerState,
    content: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                // Drawer content goes here - currently empty as requested
                MainViewMenuReturnButton(onReturnClick={
                    scope.launch{
                        drawerState.close()
                    }
                })
            }
        }
    ) {
        content()
    }
}