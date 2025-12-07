package pl.tablehub.mobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import pl.tablehub.mobile.fragments.account.profile.composables.ProfileView
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.model.v2.UserProfile
import pl.tablehub.mobile.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = "ProfileViewModel"
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun onSeeStatsClick() {
        Log.d(TAG, "Action: See Statistics and Points Clicked")
    }

    fun onChangePasswordClick() {
        Log.d(TAG, "Action: Change Password Clicked")
    }

    private val _logoutEvent = Channel<Unit>(Channel.Factory.BUFFERED)
    val logoutEvent = _logoutEvent.receiveAsFlow()
    fun onLogoutClick(guestName: String) {
        viewModelScope.launch {
            try {
                authRepository.clearData()
                Log.d(TAG, "Action: JWT Token successfully cleared.")

                _logoutEvent.send(Unit)

                _userProfile.value = UserProfile(
                    fullName = guestName,
                    email = "",
                    points = 0
                )

            } catch (e: Exception) {
                Log.e(TAG, "Error during cleaning of the token: ${e.message}", e)
            }
        }
    }
}