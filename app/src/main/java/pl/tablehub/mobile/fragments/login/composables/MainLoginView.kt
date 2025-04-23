package pl.tablehub.mobile.fragments.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.ui.shared.composables.AppLogo
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TableHubTheme

@Composable
fun MainLoginView(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit = { _: String, _: String -> },
    onRegister: () -> Unit = {},
    onForgotPassword: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val horizontalPadding = (screenWidth * 0.06f).coerceAtLeast(16f).coerceAtMost(24f).dp
    val logoSize = (screenWidth * 0.6f).coerceAtLeast(160f).coerceAtMost(240f)

    val smallSpacing = (screenHeight * 0.01f).coerceAtLeast(8f).dp
    val mediumSpacing = (screenHeight * 0.02f).coerceAtLeast(16f).dp
    val largeSpacing = (screenHeight * 0.03f).coerceAtLeast(24f).dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
            .padding(horizontal = horizontalPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(largeSpacing))
        AppLogo(imgSize = logoSize.toInt())
        Spacer(modifier = Modifier.height(mediumSpacing))
        WelcomeText()
        Spacer(modifier = Modifier.height(smallSpacing))
        SignInText()
        Spacer(modifier = Modifier.height(mediumSpacing))
        EmailInput(onValueChange = { newValue -> email = newValue })
        Spacer(modifier = Modifier.height(mediumSpacing))
        PasswordInput(onValueChange = { newValue -> password = newValue})
        Spacer(modifier = Modifier.height(smallSpacing))
        ForgotPasswordButton(onForgotPassword)
        Spacer(modifier = Modifier.height(largeSpacing))
        LogInButton { onLogin(email, password) }
        Spacer(modifier = Modifier.height(mediumSpacing))
        RegisterButton(onRegister)
        Spacer(modifier = Modifier.height(smallSpacing))
    }
}

@Preview(showBackground = true)
@Composable
fun MainLoginViewPreview() {
    TableHubTheme {
        MainLoginView()
    }
}