package pl.tablehub.mobile.fragments.signup.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun SignUpInput(
    onValueChange: (String) -> Unit = {},
    strRes: Int,
    keyboardType: KeyboardType,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = {
            Text(
                text = stringResource(strRes),
                fontSize = 16.sp,
                color = if (isError) androidx.compose.ui.graphics.Color.Red else TERTIARY_COLOR
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) androidx.compose.ui.graphics.Color.Red else PRIMARY_COLOR,
            unfocusedBorderColor = if (isError) androidx.compose.ui.graphics.Color.Red else TERTIARY_COLOR,
            focusedTextColor = TERTIARY_COLOR,
            unfocusedTextColor = TERTIARY_COLOR
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            {
                Text(
                    text = errorMessage,
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontSize = 12.sp
                )
            }
        } else null
    )
}

@Composable
fun UsernameInput(
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    SignUpInput(
        onValueChange = onValueChange,
        strRes = R.string.username,
        keyboardType = KeyboardType.Text,
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
fun EmailSignUpInput(
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    SignUpInput(
        onValueChange = onValueChange,
        strRes = R.string.email,
        keyboardType = KeyboardType.Email,
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
fun PasswordSignUpInput(
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    SignUpInput(
        onValueChange = onValueChange,
        strRes = R.string.password,
        keyboardType = KeyboardType.Password,
        isError = isError,
        errorMessage = errorMessage
    )
}

@Composable
fun ConfirmPasswordInput(
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null
) {
    SignUpInput(
        onValueChange = onValueChange,
        strRes = R.string.confirm_password,
        keyboardType = KeyboardType.Password,
        isError = isError,
        errorMessage = errorMessage
    )
}