package pl.tablehub.mobile.fragments.account.gamification.rewards

import pl.tablehub.mobile.model.v2.Reward

sealed class RewardsState {
    object Loading : RewardsState()
    data class Success(val rewards: List<Reward>) : RewardsState()
    data class Error(val message: String) : RewardsState()
    object Initial : RewardsState()
}

sealed class RewardsEvent {
    data class ShowSnackbar(val message: String) : RewardsEvent()
    object RefreshRewards : RewardsEvent()
}