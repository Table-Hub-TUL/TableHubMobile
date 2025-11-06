package pl.tablehub.mobile.fragments.account.gamification.stats.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    iconVec: ImageVector,
    value: String,
    label: String,
    iconTint: Color
) {
    val dims = rememberGlobalDimensions()

    Card(
        modifier = modifier
            .border(color = TERTIARY_COLOR, width = 2.dp, shape = RoundedCornerShape(dims.buttonCornerRadius)),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = CardDefaults.cardColors(containerColor = SECONDARY_COLOR),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.paddingBig),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = iconVec,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(dims.bigIconSize)
            )
            Spacer(modifier = Modifier.height(dims.paddingSmall))
            Text(
                text = value,
                fontSize = dims.textSizeVeryBig,
                fontWeight = FontWeight.Bold,
                color = TERTIARY_COLOR
            )
            Text(
                text = label,
                fontSize = dims.textSizeLarge,
                fontWeight = FontWeight.Medium,
                color = TERTIARY_COLOR
            )
        }
    }
}