package pl.tablehub.mobile.fragments.account.general.login.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.mapbox.common.OnValueChanged
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun ForgotPasswordCodeView(
){
    val dims = rememberGlobalDimensions()
    var password by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.height(dims.largeSpacing))
    Text(
        text = stringResource(R.string.change_your_password),
        fontSize = dims.textSizeLarge,
        fontWeight = FontWeight.Bold,
        color = TERTIARY_COLOR
    )
    Spacer(modifier = Modifier.height(dims.mediumSpacing))
    PasswordInput(onValueChange = { password = it })
}