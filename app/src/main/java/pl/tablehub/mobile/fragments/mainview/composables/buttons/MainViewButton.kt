package pl.tablehub.mobile.fragments.mainview.composables.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.ui.shared.constants.BUTTON_HEIGHT
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.shared.constants.FONT_SIZE
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun MainViewButton (
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    val dims = rememberGlobalDimensions()

    Button(
        onClick = onClick,
        modifier = modifier.height(dims.buttonHeight),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY_COLOR,
            contentColor = SECONDARY_COLOR
        ),
        contentPadding = PaddingValues(horizontal = dims.paddingSmall, vertical = dims.paddingSmall)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(dims.iconSize)
            )
            Spacer(modifier = Modifier.width(dims.smallSpacing))
        }
        Text(text = text, fontSize = dims.textSizeMedium)
    }
} 