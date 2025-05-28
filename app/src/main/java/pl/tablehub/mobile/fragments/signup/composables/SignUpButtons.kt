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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun SignUpButtonText(
    strRes: Int
) {
    Text(
        text = stringResource(strRes),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

val SIGNUP_BUTTONS_MODIFIER: Modifier = Modifier
    .fillMaxWidth()
    .height(56.dp)

val SIGNUP_BUTTONS_SHAPE: Shape = RoundedCornerShape(12.dp)

@Composable
fun CreateAccountButton(
    onCreateAccount: () -> Unit = {},
    enabled: Boolean = true
) {
    Button(
        onClick = onCreateAccount,
        modifier = SIGNUP_BUTTONS_MODIFIER,
        shape = SIGNUP_BUTTONS_SHAPE,
        colors = ButtonDefaults.buttonColors(
            containerColor = PRIMARY_COLOR
        ),
        enabled = enabled
    ) {
        SignUpButtonText(R.string.create_account)
    }
}

@Composable
fun BackToLoginButton(
    onBackToLogin: () -> Unit = {}
) {
    OutlinedButton(
        onClick = { onBackToLogin() },
        modifier = SIGNUP_BUTTONS_MODIFIER,
        shape = SIGNUP_BUTTONS_SHAPE,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TERTIARY_COLOR
        )
    ) {
        SignUpButtonText(R.string.back_to_login)
    }
}