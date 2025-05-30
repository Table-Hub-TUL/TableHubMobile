package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

@Composable
fun TableLayout(
    selectedSection: Section?,
    modifier: Modifier = Modifier
) {
    var selectedTable by remember { mutableStateOf<pl.tablehub.mobile.model.Table?>(null) }
    var chosenStatus by remember { mutableStateOf<TableStatus?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(TERTIARY_COLOR)
            .padding(horizontal = 16.dp)
    ) {
        selectedSection?.tables?.forEach { table ->
            val scaleFactor = 10
            val xOffset = (table.position.x * scaleFactor).dp
            val yOffset = (table.position.y * scaleFactor).dp

            val tableColor = when (table.status) {
                TableStatus.AVAILABLE -> GREEN_FREE_COLOR
                TableStatus.OCCUPIED -> Color.Red
                TableStatus.UNKNOWN -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .offset(x = xOffset, y = yOffset)
                    .size(32.dp)
            ) {
                IconButton(
                    onClick = {
                        selectedTable = table
                        chosenStatus = table.status
                    },
                    modifier = Modifier
                        .background(color = tableColor, shape = RoundedCornerShape(CORNER_ROUND_SIZE.dp))
                ) {}

                Text(
                    text = table.capacity.toString(),
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.Center),
                    color = SECONDARY_COLOR,
                    fontSize = 24.sp
                )
            }
        }

        if (selectedTable != null) {
            androidx.compose.material3.AlertDialog(
                containerColor = SECONDARY_COLOR,
                onDismissRequest = { selectedTable = null },
                title = {
                    Text(
                        text = stringResource(R.string.change_table_states),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = TERTIARY_COLOR
                    )
                },
                text = {
                    Column {
                        listOf(TableStatus.AVAILABLE, TableStatus.OCCUPIED).forEach { status ->
                            Button(
                                onClick = {
                                    selectedTable?.let { table ->
                                        table.status = status
                                    }
                                    selectedTable = null
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedTable?.status == status) PRIMARY_COLOR else TERTIARY_COLOR
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = when (status) {
                                        TableStatus.AVAILABLE -> stringResource(R.string.free)
                                        TableStatus.OCCUPIED -> stringResource(R.string.occupied)
                                        else -> status.name
                                    },
                                    color = SECONDARY_COLOR,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Button(
                            onClick = { selectedTable = null },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            modifier = Modifier.height(40.dp)
                        ) {
                            Text(stringResource(R.string.cancel), color = SECONDARY_COLOR)
                        }
                    }
                }
            )
        }
    }
}
