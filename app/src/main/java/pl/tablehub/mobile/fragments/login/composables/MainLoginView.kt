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
import androidx.compose.ui.tooling.preview.Preview
import pl.tablehub.mobile.ui.shared.composables.AppLogo
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TableHubTheme
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions // ✅ import your shared dimensions

@Composable
fun MainLoginView(
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onRegister: () -> Unit = {},
    onForgotPassword: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val dims = rememberGlobalDimensions()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
            .padding(horizontal = dims.horizontalPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(dims.largeSpacing))
        AppLogo(imgSize = dims.logoSize.toInt())
        Spacer(modifier = Modifier.height(dims.mediumSpacing))
        WelcomeText()
        Spacer(modifier = Modifier.height(dims.smallSpacing))
        SignInText()
        Spacer(modifier = Modifier.height(dims.mediumSpacing))
        UserNameInput(onValueChange = { username = it })
        Spacer(modifier = Modifier.height(dims.mediumSpacing))
        PasswordInput(onValueChange = { password = it })
        Spacer(modifier = Modifier.height(dims.smallSpacing))
        ForgotPasswordButton(onForgotPassword)
        Spacer(modifier = Modifier.height(dims.largeSpacing))
        LogInButton { onLogin(username, password) }
        Spacer(modifier = Modifier.height(dims.mediumSpacing))
        RegisterButton(onRegister)
        Spacer(modifier = Modifier.height(dims.smallSpacing))
    }
}

@Preview(showBackground = true)
@Composable
fun MainLoginViewPreview() {
    TableHubTheme {
        MainLoginView()
    }
}
