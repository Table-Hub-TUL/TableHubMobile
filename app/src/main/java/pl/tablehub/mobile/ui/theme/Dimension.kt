package pl.tablehub.mobile.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toSp(): TextUnit {
    val density = LocalDensity.current
    return with(density) { this@toSp.toSp() }
}


data class GlobalDimensions(
    val horizontalPadding: Dp,
    val logoSize: Float,
    val smallSpacing: Dp,
    val mediumSpacing: Dp,
    val largeSpacing: Dp,
    val textSizeSmall: TextUnit,
    val textSizeMedium: TextUnit,
    val textSizeLarge: TextUnit,
    val textSizeHuge: TextUnit,
    val buttonHeight: Dp,
    val buttonCornerRadius: Dp,
    val textFieldCornerRadius: Dp,
    val buttonTextSize: TextUnit,
    val tableTextSize: TextUnit,
    val verticalButtonPadding: Dp
)

// the values for sizes are for a approximately typical screen(width = 400, height = 800)
@Composable
fun rememberGlobalDimensions(): GlobalDimensions {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    return remember(screenHeight, screenWidth) {

        //Spacing sizes
        val smallSpacing = (screenHeight * 0.01f).coerceAtLeast(8f).dp //8dp
        val mediumSpacing = (screenHeight * 0.02f).coerceAtLeast(16f).dp //16dp
        val largeSpacing = (screenHeight * 0.03f).coerceAtLeast(24f).dp // 24dp

        // text sizes
        val textSizeSmall = (screenWidth * 0.04f).sp // ~16sp
        val textSizeMedium = (screenWidth * 0.05f).sp // ~20sp
        val textSizeLarge = (screenWidth * 0.06f).sp // ~24sp
        val textSizeHuge = (screenWidth * 0.12f).sp // ~48sp

        // Button dimensions
        val buttonHeight = (screenHeight * 0.07f).coerceIn(48f, 64f).dp //56dp
        val buttonCornerRadius = (screenWidth * 0.03f).coerceIn(8f, 16f).dp //12dp
        val textFieldCornerRadius = (screenWidth * 0.03f).coerceIn(8f, 16f).dp

        // Text sizes for UI elements
        val logoSize = (screenWidth * 0.5f).coerceIn(160f, 240f) //200dp
        val buttonTextSize = (screenWidth * 0.05f).coerceIn(18f, 22f).sp // 20sp
        val tableTextSize = (screenWidth * 0.075f).coerceIn(28f, 32f).sp // 30sp

        // Padding for UI elements
        val horizontalPadding = (screenWidth * 0.06f).coerceIn(22f, 26f).dp // 24dp
        val verticalButtonPadding = (screenHeight * 0.01f).coerceIn(6f, 12f).dp //8dp

        GlobalDimensions(
            horizontalPadding = horizontalPadding,
            logoSize = logoSize,
            smallSpacing = smallSpacing,
            mediumSpacing = mediumSpacing,
            largeSpacing = largeSpacing,
            textSizeSmall = textSizeSmall,
            textSizeMedium = textSizeMedium,
            textSizeLarge = textSizeLarge,
            textSizeHuge = textSizeHuge,
            buttonHeight = buttonHeight,
            buttonCornerRadius = buttonCornerRadius,
            textFieldCornerRadius = textFieldCornerRadius,
            buttonTextSize = buttonTextSize,
            tableTextSize =tableTextSize,
            verticalButtonPadding = verticalButtonPadding
        )
    }
}