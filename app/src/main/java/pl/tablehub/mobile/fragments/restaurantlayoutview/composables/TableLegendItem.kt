package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun TableLegendItem(
    capacity: Int,
    color: Color,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size((TABLE_SIZE_DP).dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size((TABLE_SIZE_DP).dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .padding(10.dp),
                enabled = false
            ) {}

            Text(
                text = capacity.toString(),
                modifier = Modifier.align(Alignment.Center),
                color = SECONDARY_COLOR,
                fontSize = TABLE_CAPACITY_TEXT_SIZE_SP.sp
            )
        }

        Text(
            text = description,
            color = TERTIARY_COLOR,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}