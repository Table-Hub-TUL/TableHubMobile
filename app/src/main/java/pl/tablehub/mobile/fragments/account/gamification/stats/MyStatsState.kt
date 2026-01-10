package pl.tablehub.mobile.fragments.account.gamification.stats

import pl.tablehub.mobile.client.model.user.UserStats

sealed class MyStatsState {
    object Loading : MyStatsState()
    data class Success(val stats: UserStats) : MyStatsState()
    data class Error(val message: String) : MyStatsState()
    object Initial : MyStatsState()
}