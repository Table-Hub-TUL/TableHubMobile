package pl.tablehub.mobile.model.v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.datastore.SettingsDataStore
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun onSeeStatsClick() {
        println("Action: See Statistics and Points Clicked")
    }

    fun onChangePasswordClick() {
        println("Action: Change Password Clicked")
    }

    private val _logoutEvent = Channel<Unit>(Channel.Factory.BUFFERED)
    val logoutEvent = _logoutEvent.receiveAsFlow()
    fun onLogoutClick() {
        viewModelScope.launch {
            try {
                settingsDataStore.clearAuthToken()
                println("Action: JWT Token successfully cleared.")

                _logoutEvent.send(Unit)

                _userProfile.value = UserProfile(
                    fullName = "Gość",
                    email = "",
                    points = 0
                )

            } catch (e: Exception) {
                println("Błąd podczas czyszczenia tokena: ${e.message}")
            }
        }
    }
}