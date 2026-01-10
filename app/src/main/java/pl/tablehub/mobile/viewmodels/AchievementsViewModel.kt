package pl.tablehub.mobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.fragments.account.gamification.achievements.AchievementsState
import pl.tablehub.mobile.repository.IUserRepository
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AchievementsState>(AchievementsState.Initial)
    val state: StateFlow<AchievementsState> = _state

    init {
        fetchAchievements()
    }

    fun fetchAchievements() {
        _state.value = AchievementsState.Loading
        viewModelScope.launch {
            try {
                val achievements = userRepository.getAchievements()

                val userStats = userRepository.getUserStats()
                val userPoints = userStats.points

                _state.value = AchievementsState.Success(achievements, userPoints)
            } catch (e: Exception) {
                _state.value = AchievementsState.Error("Failed to load achievements: ${e.message}")
            }

        }
    }
}