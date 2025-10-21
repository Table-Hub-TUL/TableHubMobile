package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.TableDetail
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR

private const val BUTTON_HEIGHT_DP = 48
private const val BUTTON_TEXT_SIZE_SP = 16

@Composable
fun TableStatusDialog(
    tableDetail: TableDetail,
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
                    currentStatus = tableDetail.tableStatus,
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