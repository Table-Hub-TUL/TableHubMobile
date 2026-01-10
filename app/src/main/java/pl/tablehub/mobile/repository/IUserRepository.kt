package pl.tablehub.mobile.repository

import pl.tablehub.mobile.client.model.user.UserProfileResponse
import pl.tablehub.mobile.client.model.user.UserStats
import pl.tablehub.mobile.model.v2.Achievement
import pl.tablehub.mobile.model.v2.Reward

interface IUserRepository {

    suspend fun getUserStats(): UserStats

    suspend fun getAchievements(): List<Achievement>

    suspend fun getUserRewards(): List<Reward>

    suspend fun redeemReward(rewardId: Long)
    suspend fun getLoggedInUsername(): String
    suspend fun getUserProfile(username: String): UserProfileResponse
}