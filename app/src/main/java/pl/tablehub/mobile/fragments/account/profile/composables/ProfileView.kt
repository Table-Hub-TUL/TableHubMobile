package pl.tablehub.mobile.fragments.account.profile.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.graphics.vector.ImageVector
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.profile.ProfileViewModel
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.WHITE_COLOR
import pl.tablehub.mobile.ui.theme.GRAY_TABLE_TEXT_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onLogoutAction: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val dims = rememberGlobalDimensions()
    val horizontalContentPadding = dims.paddingHuge*3f

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
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
                        text = userProfile.fullName,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = WHITE_COLOR,
                        modifier = Modifier.padding(top = dims.paddingSmall),
                        fontSize = 30.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(SECONDARY_COLOR)
                    .padding(top = dims.paddingHuge)
                    .padding(horizontal = horizontalContentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileRow(
                    icon = Icons.Default.Email,
                    text = userProfile.email,
                    color = TERTIARY_COLOR,
                    fontSizeOverride = dims.textSizeMedium,
                    modifier = Modifier.align(Alignment.Start)
                )


                ProfileRow(
                    icon = Icons.Default.Star,
                    text = stringResource(R.string.my_points, userProfile.points),
                    color = TERTIARY_COLOR,
                    fontWeight = FontWeight.Bold,
                    fontSizeOverride = dims.textSizeLarge,
                    modifier = Modifier.align(Alignment.Start)
                )

                PrimaryActionButton(
                    text = stringResource(R.string.see_stats_and_points),
                    onClick = viewModel::onSeeStatsClick
                )

                Spacer(modifier = Modifier.height(dims.paddingHuge))

                ProfileRow(
                    icon = Icons.Default.VisibilityOff,
                    text = stringResource(R.string.password, "********"),
                    color = TERTIARY_COLOR,
                    fontSizeOverride = dims.textSizeLarge,
                    modifier = Modifier.align(Alignment.Start)
                )

                PrimaryActionButton(
                    text = stringResource(R.string.change_password),
                    onClick = viewModel::onChangePasswordClick
                )

                Spacer(modifier = Modifier.height(dims.paddingHuge*15f))

                Button(
                    onClick = {
                        viewModel.onLogoutClick()
                        onLogoutAction()
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = dims.paddingLarge)
                        .height(56.dp),
                    shape = RoundedCornerShape(dims.buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = TERTIARY_COLOR)
                ) {
                    Text(
                        stringResource(R.string.logout),
                        fontSize = dims.textSizeLarge,
                        color = WHITE_COLOR,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileRow(
    icon: ImageVector,
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSizeOverride: TextUnit? = null
) {
    val dims = rememberGlobalDimensions()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dims.paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(dims.iconSize)
        )
        Text(
            text = text,
            color = color,
            fontSize = fontSizeOverride ?: dims.textSizeMedium,
            fontWeight = fontWeight,
            modifier = Modifier.padding(start = dims.paddingMedium)
        )
    }
}

@Composable
fun PrimaryActionButton(text: String, onClick: () -> Unit) {
    val dims = rememberGlobalDimensions()
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = dims.paddingLarge)
            .height(56.dp),
        shape = RoundedCornerShape(dims.buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_COLOR)
    ) {
        Text(
            text,
            fontSize = dims.textSizeMedium,
            color = WHITE_COLOR,
            fontWeight = FontWeight.Bold
        )
    }
}