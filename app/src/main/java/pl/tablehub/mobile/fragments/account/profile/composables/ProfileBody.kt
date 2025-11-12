package pl.tablehub.mobile.fragments.account.profile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.SECONDARY_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@Composable
fun ProfileBody(
    email: String,
    points: Int,
    onSeeStatsClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogout: () -> Unit,
    horizontalContentPadding: Dp,
    modifier: Modifier = Modifier
) {
    val dims = rememberGlobalDimensions()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SECONDARY_COLOR)
            .padding(top = dims.paddingHuge)
            .padding(horizontal = horizontalContentPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileRow(
            icon = Icons.Default.Email,
            text = email,
            color = TERTIARY_COLOR,
            fontSizeOverride = dims.textSizeMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        ProfileRow(
            icon = Icons.Default.Star,
            text = stringResource(R.string.my_points, points),
            color = TERTIARY_COLOR,
            fontWeight = FontWeight.Bold,
            fontSizeOverride = dims.textSizeLarge,
            modifier = Modifier.align(Alignment.Start)
        )

        PrimaryActionButton(
            text = stringResource(R.string.see_stats_and_points),
            onClick = onSeeStatsClick
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
            onClick = onChangePasswordClick
        )

        Spacer(modifier = Modifier.height(dims.paddingHuge * 15f))

        PrimaryActionButton(
            text = stringResource(R.string.logout),
            onClick = onLogout,
            containerColor = TERTIARY_COLOR,
            textSizeOverride = dims.textSizeLarge.value
        )
    }
}