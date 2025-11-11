package pl.tablehub.mobile.fragments.account.profile.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.WHITE_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun LogoutButton(onClick: () -> Unit) {
    val dims = rememberGlobalDimensions()
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = dims.paddingLarge)
            .height(56.dp),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(containerColor = TERTIARY_COLOR)
    ) {
        Text(
            stringResource(R.string.logout),
            fontSize = dims.textSizeLarge,
            color = WHITE_COLOR,
            fontWeight = FontWeight.Bold
        )
    }
}