package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import pl.tablehub.mobile.model.Table
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.client.model.TableStatusChange
import pl.tablehub.mobile.ui.shared.constants.CORNER_ROUND_SIZE
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

private const val POSITION_SCALE_FACTOR = 10
const val TABLE_SIZE_DP = 32
const val TABLE_CAPACITY_TEXT_SIZE_SP = 24
private const val BUTTON_HEIGHT_DP = 48
private const val BUTTON_TEXT_SIZE_SP = 16

@Composable
fun TableLayout(
    restaurantID: Long = -1,
    selectedSection: Section?,
    modifier: Modifier = Modifier,
    onTableStatusChanged: ((TableStatusChange) -> Unit) = { _: TableStatusChange -> }
) {
    var selectedTable by remember { mutableStateOf<Table?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(TERTIARY_COLOR)
            .padding(horizontal = 16.dp)
    ) {
        selectedSection?.tables?.forEach { table ->
            TableItem(
                table = table,
                onTableClick = { selectedTable = it }
            )
        }

        selectedTable?.let { table ->
            TableStatusDialog(
                table = table,
                onDismiss = { selectedTable = null },
                onStatusChange = { newStatus ->
                    table.status = newStatus
                    onTableStatusChanged (
                        TableStatusChange(
                            restaurantID,
                            selectedSection!!.id,
                            table.id,
                            newStatus
                        )
                    )
                    selectedTable = null
                }
            )
        }
    }
}

@Composable
private fun TableItem(
    table: Table,
    onTableClick: (Table) -> Unit,
    modifier: Modifier = Modifier
) {
    val xOffset = (table.positionX * POSITION_SCALE_FACTOR).dp
    val yOffset = (table.positionY * POSITION_SCALE_FACTOR).dp
    val tableColor = getTableColor(table.status)

    Box(
        modifier = modifier
            .offset(x = xOffset, y = yOffset)
            .size(TABLE_SIZE_DP.dp)
    ) {
        IconButton(
            onClick = { onTableClick(table) },
            modifier = Modifier
                .background(
                    color = tableColor,
                    shape = RoundedCornerShape(90.dp)
                )
        ) {}

        Text(
            text = table.capacity.toString(),
            modifier = Modifier.align(Alignment.Center),
            color = SECONDARY_COLOR,
            fontSize = TABLE_CAPACITY_TEXT_SIZE_SP.sp
        )
    }
}

@Composable
private fun TableStatusDialog(
    table: Table,
    onDismiss: () -> Unit,
    onStatusChange: (TableStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        containerColor = SECONDARY_COLOR,
        onDismissRequest = onDismiss,
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
                TableStatusOptions(
                    currentStatus = table.status,
                    onStatusSelect = onStatusChange
                )
            }
        },
        confirmButton = {
            DialogActions(onCancel = onDismiss)
        }
    )
}

@Composable
private fun TableStatusOptions(
    currentStatus: TableStatus,
    onStatusSelect: (TableStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val availableStatuses = listOf(TableStatus.AVAILABLE, TableStatus.OCCUPIED)

    Column(modifier = modifier) {
        availableStatuses.forEach { status ->
            StatusButton(
                status = status,
                isSelected = currentStatus == status,
                onClick = { onStatusSelect(status) }
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun StatusButton(
    status: TableStatus,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PRIMARY_COLOR else TERTIARY_COLOR
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT_DP.dp)
    ) {
        Text(
            text = getStatusDisplayText(status),
            color = SECONDARY_COLOR,
            fontSize = BUTTON_TEXT_SIZE_SP.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun DialogActions(
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Button(
            onClick = onCancel,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = stringResource(R.string.cancel),
                color = SECONDARY_COLOR
            )
        }
    }
}

@Composable
private fun getStatusDisplayText(status: TableStatus): String {
    return when (status) {
        TableStatus.AVAILABLE -> stringResource(R.string.free)
        TableStatus.OCCUPIED -> stringResource(R.string.occupied)
        else -> status.name
    }
}

private fun getTableColor(status: TableStatus): Color {
    return when (status) {
        TableStatus.AVAILABLE -> GREEN_FREE_COLOR
        TableStatus.OCCUPIED -> Color.Red
        TableStatus.UNKNOWN -> Color.Gray
    }
}