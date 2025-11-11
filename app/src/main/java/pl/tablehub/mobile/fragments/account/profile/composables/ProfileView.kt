package pl.tablehub.mobile.fragments.account.profile.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.tablehub.mobile.model.v2.ProfileViewModel
import pl.tablehub.mobile.ui.theme.rememberGlobalDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onLogoutAction: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val dims = rememberGlobalDimensions()
    val horizontalContentPadding = dims.paddingHuge * 3f

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
            ProfileHeader(
                fullName = userProfile.fullName,
                onBackClick = onBackClick,
                modifier = Modifier
                    .weight(0.3f)
            )

            ProfileBody(
                email = userProfile.email,
                points = userProfile.points,
                onSeeStatsClick = viewModel::onSeeStatsClick,
                onChangePasswordClick = viewModel::onChangePasswordClick,
                onLogout = {
                    viewModel.onLogoutClick()
                    onLogoutAction()
                },
                horizontalContentPadding = horizontalContentPadding,
                modifier = Modifier
                    .weight(0.7f)
            )
        }
    }
}