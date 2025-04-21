package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun MainMapView() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MainViewMenu(drawerState = drawerState) {
        Box(modifier = Modifier.fillMaxSize()) {
            MapboxMapWrapper()
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBarContent(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onFilterClick = { /* TODO: Implement filter action */ }
                )
                Box(modifier = Modifier.weight(1f))
                BottomButtons(
                    onReportClick = { /* TODO: Implement report action */ },
                    onLocationClick = { /* TODO: Implement location action */ }
                )
            }
        }
    }
}