package pl.tablehub.mobile.ui.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.R

@Composable
fun AppLogo(
    imgSize: Int
) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "TableHub Logo",
        modifier = Modifier.size(imgSize.dp)
    )
}