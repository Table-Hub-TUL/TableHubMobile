package pl.tablehub.mobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.tablehub.mobile.fragments.account.gamification.rewards.RewardsEvent
import pl.tablehub.mobile.fragments.account.gamification.rewards.RewardsState
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.repository.IUserRepository
import javax.inject.Inject

@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val restaurantsRepository: IRestaurantsRepository // Injected Repository
) : ViewModel() {

    private val _state = MutableStateFlow<RewardsState>(RewardsState.Initial)
    val state: StateFlow<RewardsState> = _state

    private val _events = Channel<RewardsEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        fetchRewards()
    }

    fun fetchRewards() {
        _state.value = RewardsState.Loading
        viewModelScope.launch {
            try {
                // Fetch rewards from all restaurants instead of user rewards
                val rewards = restaurantsRepository.getAllRestaurantsRewards()
                _state.value = RewardsState.Success(rewards)
            } catch (e: Exception) {
                _state.value = RewardsState.Error("Failed to load rewards: ${e.message}")
            }
        }
    }

    fun redeemReward(rewardId: Long) {
        viewModelScope.launch {
            try {
                userRepository.redeemReward(rewardId)

                _events.send(RewardsEvent.ShowSnackbar("Nagroda zrealizowana pomyślnie!"))
                _events.send(RewardsEvent.RefreshRewards)
                fetchRewards()
            } catch (e: Exception) {
                _events.send(RewardsEvent.ShowSnackbar("Błąd realizacji: ${e.message}"))
            }
        }
    }
}