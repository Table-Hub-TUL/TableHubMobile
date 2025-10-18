package pl.tablehub.mobile.fragments.signup.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun SignUpButtonText(
    strRes: Int,
    fontSize: TextUnit
) {
    Text(
        text = stringResource(strRes),
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SignUpBaseButton(
    textResId: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isOutlined: Boolean = false,
    containerColor: Color = PRIMARY_COLOR,
    contentColor: Color = TERTIARY_COLOR
) {
    val dims = rememberGlobalDimensions()
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(dims.buttonHeight)

    val buttonShape = RoundedCornerShape(dims.buttonCornerRadius)

    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            shape = buttonShape,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = contentColor
            ),
            enabled = enabled
        ) {
            SignUpButtonText(textResId, dims.textSizeMedium)
        }
    } else {
        Button(
            onClick = onClick,
            modifier = buttonModifier,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor
            ),
            enabled = enabled
        ) {
            SignUpButtonText(textResId, dims.textSizeMedium)
        }
    }
}

@Composable
fun CreateAccountButton(
    onCreateAccount: () -> Unit = {},
    enabled: Boolean = true
) {
    SignUpBaseButton(
        textResId = R.string.create_account,
        onClick = onCreateAccount,
        enabled = enabled,
        isOutlined = false
    )
}

@Composable
fun BackToLoginButton(
    onBackToLogin: () -> Unit = {}
) {
    SignUpBaseButton(
        textResId = R.string.back_to_login,
        onClick = onBackToLogin,
        isOutlined = true
    )
}
