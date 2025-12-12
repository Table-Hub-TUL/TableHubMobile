package pl.tablehub.mobile.fragments.account.gamification.achievements.composables

import pl.tablehub.mobile.model.v2.Achievement

internal fun getVisualsForAchievement(achievement: Achievement): Pair<String, String> {
    return when (achievement.id.toInt()) {
        1 -> "🍞" to "0-500 pts"
        2 -> "🥗" to "500-1000 pts"
        3 -> "👨‍🍳" to "1000-1500 pts"
        4 -> "🌟" to "1500-2000 pts"
        5 -> "👑" to "2000-2500 pts"
        6 -> "💎" to "2500-3000 pts"
        else -> "🏆" to "${achievement.points} pts"
    }
}