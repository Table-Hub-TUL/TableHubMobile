package pl.tablehub.mobile.fragments.mainview.composables

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

@Composable
fun TableFilter(
    minFreeTables: Int,
    onMinFreeTablesChanged: (Int) -> Unit
) {
    OutlinedTextField(
        value = minFreeTables.toString(),
        onValueChange = {
            onMinFreeTablesChanged(it.toIntOrNull() ?: 0)
        },
        label = {
            Text(
                text = stringResource(R.string.minimum_free_seats),
                fontSize = 16.sp,
                color = TERTIARY_COLOR
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        visualTransformation = VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PRIMARY_COLOR,
            unfocusedBorderColor = TERTIARY_COLOR
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}