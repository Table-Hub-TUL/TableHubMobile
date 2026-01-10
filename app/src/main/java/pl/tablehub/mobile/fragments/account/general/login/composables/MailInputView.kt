package pl.tablehub.mobile.fragments.account.general.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.shared.composables.AppLogo
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun MailInputView(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = rememberGlobalDimensions()
    var email by remember { mutableStateOf("") }

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
        Text(
            text = stringResource(R.string.input_email),
            fontSize = dims.textSizeLarge,
            fontWeight = FontWeight.Bold,
            color = TERTIARY_COLOR
        )

        Spacer(modifier = Modifier.height(dims.mediumSpacing))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                onValueChange(it)
            },
            label = {
                Text(
                    text = stringResource(R.string.email),
                    fontSize = dims.textSizeSmall,
                    color = TERTIARY_COLOR
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            // Updated to textFieldCornerRadius to match MainLoginView inputs
            shape = RoundedCornerShape(dims.textFieldCornerRadius),
            visualTransformation = VisualTransformation.None,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PRIMARY_COLOR,
                unfocusedBorderColor = TERTIARY_COLOR,
                focusedTextColor = TERTIARY_COLOR,
                unfocusedTextColor = TERTIARY_COLOR
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
    }
}