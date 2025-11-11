package pl.tablehub.mobile.fragments.account.profile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.WHITE_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun ProfileHeader(
    fullName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = rememberGlobalDimensions()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(TERTIARY_COLOR),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(top = dims.paddingSmall, start = dims.paddingMedium)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = WHITE_COLOR,
                    modifier = Modifier.size(dims.iconSize * 1.5f)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dims.paddingLarge),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.Black, shape = CircleShape)
                    .padding(dims.paddingSmall)
            )
            Spacer(modifier = Modifier.height(dims.paddingHuge))
            Text(
                text = fullName,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = WHITE_COLOR,
                modifier = Modifier.padding(top = dims.paddingSmall),
                fontSize = 30.sp
            )
        }
    }
}