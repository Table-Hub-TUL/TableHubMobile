package pl.tablehub.mobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.model.v2.UserProfile
import pl.tablehub.mobile.repository.AuthRepository
import javax.inject.Inject
import pl.tablehub.mobile.repository.IUserRepository

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    private val TAG = "ProfileViewModel"

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    private val _logoutEvent = Channel<Unit>(Channel.Factory.BUFFERED)
    val logoutEvent = _logoutEvent.receiveAsFlow()

    init {
        loadUserProfileData()
    }

    fun loadUserProfileData() {
        viewModelScope.launch {
            try {
                val stats = userRepository.getUserStats()

                _userProfile.value = UserProfile(
                    points = stats.points
                )

            } catch (e: Exception) {
                Log.e(TAG, "Failed to load user profile data or points: ${e.message}")
                _userProfile.value = _userProfile.value.copy(points = 0)
            }
        }
    }
    fun onSeeStatsClick() {
        Log.d(TAG, "Action: See Statistics and Points Clicked")
    }

    fun onChangePasswordClick() {
        Log.d(TAG, "Action: Change Password Clicked")
    }

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