package pl.tablehub.mobile.ui.shared.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun BackButton(
    onBackClick: () -> Unit
) {
    val dims = rememberGlobalDimensions()

    IconButton(
        onClick = onBackClick,
        modifier = Modifier
            .size(dims.buttonHeight)
            .clip(RoundedCornerShape(dims.buttonCornerRadius))
            .background(SECONDARY_COLOR)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = TERTIARY_COLOR,
            modifier = Modifier.size(dims.iconSize)
        )
    }
}