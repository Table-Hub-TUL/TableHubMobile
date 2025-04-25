package pl.tablehub.mobile.ui.shared.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR

@Composable
fun PopUpWrapper(
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(CORNER_ROUND_SIZE.dp),
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .background(Color.Transparent),
            border = BorderStroke(3.dp, PRIMARY_COLOR),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = SECONDARY_COLOR)
        ) {
            content()
        }
    }
}