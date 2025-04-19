package pl.tablehub.mobile.fragments.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppLogo(imgSize = 240)
        Spacer(modifier = Modifier.height(32.dp))
        WelcomeText()
        Spacer(modifier = Modifier.height(8.dp))
        SignInText()
        Spacer(modifier = Modifier.height(32.dp))
        EmailInput(onValueChange = { newValue -> email = newValue })
        Spacer(modifier = Modifier.height(16.dp))
        PasswordInput(onValueChange = { newValue -> password = newValue})
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPasswordButton(onForgotPassword)
        Spacer(modifier = Modifier.height(24.dp))
        LogInButton { onLogin(email, password) }
        Spacer(modifier = Modifier.height(16.dp))
        RegisterButton(onRegister)
    }
}

@Preview(showBackground = true)
@Composable
fun MainLoginViewPreview() {
    TableHubTheme {
        MainLoginView()
    }
}