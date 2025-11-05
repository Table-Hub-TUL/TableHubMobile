package pl.tablehub.mobile.fragments.account.gamification.stats.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit
) {
    val dims = rememberGlobalDimensions()

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(dims.buttonHeight),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(containerColor = TERTIARY_COLOR)
    ) {
        Text(
            text = text,
            fontSize = dims.textSizeLarge,
            fontWeight = FontWeight.Bold,
            color = SECONDARY_COLOR
        )
    }
}