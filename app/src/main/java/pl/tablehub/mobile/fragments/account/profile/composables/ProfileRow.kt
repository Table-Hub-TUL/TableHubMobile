package pl.tablehub.mobile.fragments.account.profile.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun ProfileRow(
    icon: ImageVector,
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSizeOverride: TextUnit? = null
) {
    val dims = rememberGlobalDimensions()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dims.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(dims.iconSize)
        )
        Text(
            text = text,
            color = color,
            fontSize = fontSizeOverride ?: dims.textSizeMedium,
            fontWeight = fontWeight,
            modifier = Modifier.padding(start = dims.paddingMedium)
        )
    }
}