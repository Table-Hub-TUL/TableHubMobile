package pl.tablehub.mobile.fragments.account.general.signup.composables

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import pl.tablehub.mobile.ui.shared.composables.AppLogo
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TableHubTheme
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun MainSignUpView(
    modifier: Modifier = Modifier,
    onCreate: (String, String, String) -> Unit = { _: String, _: String, _: String -> },
    onBackToLogin: () -> Unit = {}
) {
    val dims = rememberGlobalDimensions()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val validationState by remember {
        derivedStateOf {
            SignUpValidator.validateSignUpForm(username, email, password, confirmPassword)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SECONDARY_COLOR)
            .padding(horizontal = dims.horizontalPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(dims.paddingLarge))

        AppLogo(imgSize = dims.logoSize.toInt())

        Spacer(modifier = Modifier.height(dims.paddingLarge))

        JoinUsText()

        Spacer(modifier = Modifier.height(dims.paddingSmall))

        CreateAccountText()

        Spacer(modifier = Modifier.height(dims.paddingHuge))

        UsernameInput(
            onValueChange = { newValue -> username = newValue },
            isError = !validationState.username.isValid && username.isNotEmpty(),
            errorMessage = validationState.username.errorMessage
        )

        Spacer(modifier = Modifier.height(dims.paddingLarge))

        EmailSignUpInput(
            onValueChange = { newValue -> email = newValue },
            isError = !validationState.email.isValid && email.isNotEmpty(),
            errorMessage = validationState.email.errorMessage
        )

        Spacer(modifier = Modifier.height(dims.paddingLarge))

        PasswordSignUpInput(
            onValueChange = { newValue -> password = newValue },
            isError = !validationState.password.isValid && password.isNotEmpty(),
            errorMessage = validationState.password.errorMessage
        )

        Spacer(modifier = Modifier.height(dims.paddingLarge))

        ConfirmPasswordInput(
            onValueChange = { newValue -> confirmPassword = newValue },
            isError = !validationState.confirmPassword.isValid && confirmPassword.isNotEmpty(),
            errorMessage = validationState.confirmPassword.errorMessage
        )

        Spacer(modifier = Modifier.height(dims.paddingHuge *2))

        CreateAccountButton(
            onCreateAccount = { onCreate(username, email, password) },
            enabled = validationState.isAllValid &&
                    username.isNotEmpty() &&
                    email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    confirmPassword.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(dims.paddingLarge))

        BackToLoginButton(onBackToLogin)

        Spacer(modifier = Modifier.height(dims.paddingSmall))
    }
}

@Preview(showBackground = true)
@Composable
fun MainSignUpViewPreview() {
    TableHubTheme {
        MainSignUpView()
    }
}