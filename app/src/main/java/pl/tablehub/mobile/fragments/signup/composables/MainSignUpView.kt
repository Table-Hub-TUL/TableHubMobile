package pl.tablehub.mobile.fragments.signup.composables

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

@Composable
fun MainSignUpView(
    modifier: Modifier = Modifier,
    onCreate: (String, String, String) -> Unit = { _: String, _: String, _: String -> },
    onBackToLogin: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    val validationState by remember {
        derivedStateOf {
            SignUpValidator.validateSignUpForm(username, email, password, confirmPassword, context)
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp

    val horizontalPadding = (screenWidth * 0.06f).coerceAtLeast(16f).coerceAtMost(24f).dp
    val logoSize = (screenWidth * 0.5f).coerceAtLeast(120f).coerceAtMost(180f)

    val smallSpacing = (screenHeight * 0.01f).coerceAtLeast(8f).dp
    val mediumSpacing = (screenHeight * 0.015f).coerceAtLeast(12f).dp
    val largeSpacing = (screenHeight * 0.025f).coerceAtLeast(20f).dp

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

        JoinUsText()

        Spacer(modifier = Modifier.height(smallSpacing))

        CreateAccountText()

        Spacer(modifier = Modifier.height(largeSpacing))

        UsernameInput(
            onValueChange = { newValue -> username = newValue },
            isError = !validationState.username.isValid && username.isNotEmpty(),
            errorMessage = validationState.username.errorMessage
        )

        Spacer(modifier = Modifier.height(mediumSpacing))

        EmailSignUpInput(
            onValueChange = { newValue -> email = newValue },
            isError = !validationState.email.isValid && email.isNotEmpty(),
            errorMessage = validationState.email.errorMessage
        )

        Spacer(modifier = Modifier.height(mediumSpacing))

        PasswordSignUpInput(
            onValueChange = { newValue -> password = newValue },
            isError = !validationState.password.isValid && password.isNotEmpty(),
            errorMessage = validationState.password.errorMessage
        )

        Spacer(modifier = Modifier.height(mediumSpacing))

        ConfirmPasswordInput(
            onValueChange = { newValue -> confirmPassword = newValue },
            isError = !validationState.confirmPassword.isValid && confirmPassword.isNotEmpty(),
            errorMessage = validationState.confirmPassword.errorMessage
        )

        Spacer(modifier = Modifier.height(2 * largeSpacing))

        CreateAccountButton(
            onCreateAccount = { onCreate(username, email, password) },
            enabled = validationState.isAllValid &&
                    username.isNotEmpty() &&
                    email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    confirmPassword.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(mediumSpacing))

        BackToLoginButton(onBackToLogin)

        Spacer(modifier = Modifier.height(smallSpacing))
    }
}

@Preview(showBackground = true)
@Composable
fun MainSignUpViewPreview() {
    TableHubTheme {
        MainSignUpView()
    }
}