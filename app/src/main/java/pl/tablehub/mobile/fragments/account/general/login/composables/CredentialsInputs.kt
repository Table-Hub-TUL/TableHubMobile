package pl.tablehub.mobile.fragments.account.general.login.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun LogInInput(
    onValueChange: (String) -> Unit = {},
    strRes: Int,
    keyboardType: KeyboardType
) {
    val dims = rememberGlobalDimensions()
    var value by remember { mutableStateOf("")}

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = {
            Text(
                text = stringResource(strRes),
                fontSize = dims.textSizeMedium,
                color = TERTIARY_COLOR
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(dims.textFieldCornerRadius),
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PRIMARY_COLOR,
            unfocusedBorderColor = TERTIARY_COLOR
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Composable
fun UserNameInput(
    onValueChange: (String) -> Unit = {}
) {
    LogInInput(
        onValueChange = onValueChange,
        strRes = R.string.username,
        keyboardType = KeyboardType.Text
    )
}

@Composable
fun PasswordInput(
    onValueChange: (String) -> Unit = {}
) {
    LogInInput(
        onValueChange = onValueChange,
        strRes = R.string.password,
        keyboardType = KeyboardType.Password
    )
}

@Composable
fun MainButtonText(
    strRes: Int
) {
    val dims = rememberGlobalDimensions()

    Text(
        text = stringResource(strRes),
        fontSize = dims.textSizeLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LogInButton(
    onLogin: () -> Unit = {}
) {
    val dims = rememberGlobalDimensions()

    Button(
        onClick = onLogin,
        modifier = Modifier
            .fillMaxWidth()
            .height(dims.buttonHeight),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY_COLOR
        )
    ) {
        MainButtonText(R.string.log_in)
    }
}

@Composable
fun RegisterButton(
    onRegister: () -> Unit = {}
) {
    val dims = rememberGlobalDimensions()

    OutlinedButton(
        onClick = { onRegister() },
        modifier = Modifier
            .fillMaxWidth()
            .height(dims.buttonHeight),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TERTIARY_COLOR
        )
    ) {
        MainButtonText(R.string.create_account)
    }
}

@Composable
fun ForgotPasswordButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = rememberGlobalDimensions()

    TextButton(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = dims.paddingSmall)
    ) {
        Text(
            text = stringResource(R.string.forgot_password),
            color = TERTIARY_COLOR
        )
    }
}

@Composable
fun SignInText() {
    val dims = rememberGlobalDimensions()

    Text(
        text = stringResource(R.string.sign_in_to_cont),
        fontSize = dims.textSizeMedium,
        color = TERTIARY_COLOR.copy(alpha = 0.7f)
    )
}

@Composable
fun WelcomeText() {
    val dims = rememberGlobalDimensions()

    Text(
        text = stringResource(R.string.welcome_back),
        fontSize = dims.textSizeLarge,
        fontWeight = FontWeight.Bold,
        color = TERTIARY_COLOR
    )
}