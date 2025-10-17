package pl.tablehub.mobile.fragments.mainview.composables.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun FilterOverlay(onOverlayClick: () -> Unit) {
    val dims = rememberGlobalDimensions()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(Modifier.fillMaxWidth().minus(dims.drawerWidth))
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onOverlayClick)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(dims.drawerWidth)
                .align(Alignment.CenterEnd)
                .background(Color.Black.copy(alpha = 0.15f))
                .clickable(onClick = { })
        )
    }
}