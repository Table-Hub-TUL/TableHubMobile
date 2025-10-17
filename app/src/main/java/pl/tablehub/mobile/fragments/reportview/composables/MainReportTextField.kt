package pl.tablehub.mobile.fragments.reportview.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun RestaurantInput(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    strRes: Int,
    keyboardType: KeyboardType
) {
    val dims = rememberGlobalDimensions()
    var inputValue by remember { mutableStateOf(value) }
    OutlinedTextField(
        value = inputValue,
        onValueChange = {
            inputValue = it
            onValueChange(it)
        },
        label = {
            Text(
                text = stringResource(strRes),
                fontSize = dims.textSizeSmall,
                color = TERTIARY_COLOR
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dims.paddingHuge),
        singleLine = true,
        shape = RoundedCornerShape(dims.buttonCornerRadius),
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
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
                tint = TERTIARY_COLOR
            )
        }
    )
}

@Composable
fun MainReportTextField(
    onValueChange: (String) -> Unit = {}
) {
    RestaurantInput(
        onValueChange = onValueChange,
        strRes = R.string.restaurant_name,
        keyboardType = KeyboardType.Text
    )
}