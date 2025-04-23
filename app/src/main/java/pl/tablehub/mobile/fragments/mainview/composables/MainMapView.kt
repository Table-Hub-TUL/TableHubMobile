package pl.tablehub.mobile.fragments.mainview.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.model.Restaurant

@Composable
fun MainMapView(restaurants: List<Restaurant>) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val locationTrigger = remember { MutableSharedFlow<Unit>(extraBufferCapacity = 1) }

    MainViewMenu(drawerState = drawerState) {
        Box(modifier = Modifier.fillMaxSize()) {
            MapboxMapWrapper(locationTrigger = locationTrigger, restaurants = restaurants)
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
                    onLocationClick = {
                        scope.launch {
                            locationTrigger.tryEmit(Unit)
                        }
                    }
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        scope.launch {
            locationTrigger.emit(Unit)
        }
    }
}