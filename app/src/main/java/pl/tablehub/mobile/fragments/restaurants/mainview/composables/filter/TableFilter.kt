package pl.tablehub.mobile.fragments.restaurants.mainview.composables.filter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun TableFilter(
    minFreeTables: Int,
    onMinFreeTablesChanged: (Int) -> Unit
) {
    val dims = rememberGlobalDimensions()

    OutlinedTextField(
        value = minFreeTables.toString(),
        onValueChange = {
            onMinFreeTablesChanged(it.toIntOrNull() ?: 0)
        },
        label = {
            Text(
                text = stringResource(R.string.minimum_free_seats),
                fontSize = dims.textSizeSmall,
                color = TERTIARY_COLOR
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        visualTransformation = VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PRIMARY_COLOR,
            unfocusedBorderColor = TERTIARY_COLOR
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}