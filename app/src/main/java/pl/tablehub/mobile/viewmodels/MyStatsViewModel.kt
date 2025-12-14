package pl.tablehub.mobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.fragments.account.gamification.stats.MyStatsState
import pl.tablehub.mobile.repository.IUserRepository
import javax.inject.Inject

@HiltViewModel
class MyStatsViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MyStatsState>(MyStatsState.Initial)
    val state: StateFlow<MyStatsState> = _state

    init {
        fetchUserStats()
    }

    fun fetchUserStats() {
        _state.value = MyStatsState.Loading
        viewModelScope.launch {
            try {
                val stats = userRepository.getUserStats()
                _state.value = MyStatsState.Success(stats)
            } catch (e: Exception) {
                _state.value = MyStatsState.Error("Failed to load statistics: ${e.message}")
            }
        }
    }

}