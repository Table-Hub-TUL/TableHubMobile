package pl.tablehub.mobile.fragments.account.gamification.achievements

import pl.tablehub.mobile.model.v2.Achievement

sealed class AchievementsState {
    object Loading : AchievementsState()
    data class Success(
        val achievements: List<Achievement>,
        val userPoints: Int
    ) : AchievementsState()
    data class Error(val message: String) : AchievementsState()
    object Initial : AchievementsState()
}