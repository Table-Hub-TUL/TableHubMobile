package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.fragments.restaurantlayoutview.temp.exampleTableDetail
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.TableDetail
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI


const val BASE_TABLE_SIZE_DP = 32
const val BASE_CHAIR_SIZE_DP = 24
const val TABLE_CAPACITY_TEXT_SIZE_SP = 24

private fun getTableColor(status: TableStatus): Color {
    return when (status) {
        TableStatus.AVAILABLE -> GREEN_FREE_COLOR
        TableStatus.OCCUPIED -> Color.Red
        TableStatus.UNKNOWN -> Color.Gray
    }
}

private fun calculateTableSize(capacity: Int): Dp {
    return (BASE_TABLE_SIZE_DP + (capacity * 4)).dp
}

private fun calculateChairDistance(tableSize: Dp): Dp {
    return tableSize * 0.55f
}

@Preview
@Composable
fun TableItem(
    tableDetail: TableDetail = exampleTableDetail,
    onTableClick: (TableDetail) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val xOffset = (tableDetail.position.x).dp
    val yOffset = (tableDetail.position.y).dp
    val tableColor = getTableColor(tableDetail.tableStatus)

    val tableSize = calculateTableSize(tableDetail.capacity)
    val chairDistance = calculateChairDistance(tableSize)

    Box(
        modifier = modifier
            .offset(x = xOffset, y = yOffset)
            .size(tableSize)
    ) {
        repeat(tableDetail.capacity) { index ->
            val angle = (2 * PI * index / tableDetail.capacity) - (PI / 2)
            val angleInDegrees = Math.toDegrees(angle).toFloat()
            val chairX = (chairDistance.value * cos(angle)).dp
            val chairY = (chairDistance.value * sin(angle)).dp

            Chair(
                color = tableColor,
                modifier = Modifier
                    .offset(
                        x = chairX + (tableSize - BASE_CHAIR_SIZE_DP.dp) / 2,
                        y = chairY + (tableSize - BASE_CHAIR_SIZE_DP.dp) / 2
                    )
                    .graphicsLayer(
                        rotationZ = angleInDegrees + 90f
                    )
            )
        }

        Box(
            modifier = Modifier
                .size(tableSize)
                .background(
                    color = tableColor,
                    shape = RoundedCornerShape(90.dp)
                )
        ) {
            IconButton(
                onClick = { onTableClick(tableDetail) },
                modifier = Modifier.size(tableSize)
            ) {}
        }

        Text(
            text = tableDetail.capacity.toString(),
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = TABLE_CAPACITY_TEXT_SIZE_SP.sp
        )
    }
}