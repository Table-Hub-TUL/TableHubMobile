package pl.tablehub.mobile.repository

import kotlinx.coroutines.flow.first
import pl.tablehub.mobile.client.model.user.UserStats
import pl.tablehub.mobile.client.rest.interfaces.IUserService
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.model.v2.Achievement
import pl.tablehub.mobile.model.v2.Reward
import javax.inject.Inject
import pl.tablehub.mobile.datastore.EncryptedDataStore.Companion.USERNAME_KEY

class UserRepositoryImpl @Inject constructor(
    private val userService: IUserService,
    private val encryptedDataStore: EncryptedDataStore
) : IUserRepository {

    private suspend fun getUsername(): String {
        return encryptedDataStore.get(EncryptedDataStore.USERNAME_KEY)
            .first()
            ?: throw IllegalStateException("Username missing in DataStore.")
    }

    override suspend fun getUserStats(): UserStats {
        val username = getUsername()
        return userService.getUserStats(username)
    }

    override suspend fun getAchievements(): List<Achievement> {
        return userService.getAchievements()
    }

    override suspend fun getUserRewards(): List<Reward> {
        val username = getUsername()
        return userService.getUserRewards(username)
    }

    override suspend fun redeemReward(rewardId: Long) {
        val username = getUsername()
        userService.redeemReward(username, rewardId)
    }
}