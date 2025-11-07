package pl.tablehub.mobile.fragments.account.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class UserProfile(
    val fullName: String = "John Doe",
    val email: String = "john.doe@example.com",
    val points: Int = 2000
)

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun onSeeStatsClick() {
        println("Action: See Statistics and Points Clicked")
    }

    fun onChangePasswordClick() {
        println("Action: Change Password Clicked")
    }

    fun onLogoutClick() {
        println("Action: Logout preparation complete")
    }
}