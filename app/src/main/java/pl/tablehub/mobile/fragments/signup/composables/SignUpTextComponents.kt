package pl.tablehub.mobile.fragments.signup.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun CreateAccountText() {
    val dims = rememberGlobalDimensions()
    Text(
        text = stringResource(R.string.create_new_account),
        fontSize = dims.textSizeSmall,
        color = TERTIARY_COLOR.copy(alpha = 0.7f)
    )
}

@Composable
fun JoinUsText() {
    val dims = rememberGlobalDimensions()
    Text(
        text = stringResource(R.string.join_us_today),
        fontSize = dims.textSizeLarge,
        fontWeight = FontWeight.Bold,
        color = TERTIARY_COLOR
    )
}