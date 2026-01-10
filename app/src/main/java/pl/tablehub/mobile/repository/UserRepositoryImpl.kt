package pl.tablehub.mobile.repository

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import pl.tablehub.mobile.client.model.user.UserProfileResponse
import pl.tablehub.mobile.client.model.user.UserStats
import pl.tablehub.mobile.client.rest.interfaces.IUserService
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.model.v2.Achievement
import pl.tablehub.mobile.model.v2.Reward
import javax.inject.Inject
import pl.tablehub.mobile.model.v2.RewardDto
import pl.tablehub.mobile.model.v2.Image
import pl.tablehub.mobile.model.v2.Address
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


    override suspend fun getAchievements(): List<Achievement> {
        return userService.getAchievements()
    }

    override suspend fun getUserRewards(): List<Reward> {
        val username = getUsername()
        val response = userService.getUserRewards(username)

        return response.map { dto ->
            Reward(
                id = dto.id,
                title = dto.title,
                additionalDescription = dto.additionalDescription,
                image = Image(
                    url = dto.image,
                    altText = dto.title,
                    ratio = 1.0
                ),
                restaurantName = dto.restaurantName,
                restaurantAddress = Address(
                    street = dto.street,
                    city = dto.city,
                    streetNumber = 0, apartmentNumber = null, postalCode = "", country = ""
                ),
                redeemed = dto.redeemed
            )
        }
    }

    override suspend fun redeemReward(rewardId: Long) {
        val username = getUsername()
        userService.redeemReward(username, rewardId)
    }

    override suspend fun getLoggedInUsername(): String {
        return encryptedDataStore.get(EncryptedDataStore.USERNAME_KEY).first() ?: "Guest"
    }
    override suspend fun getUserProfile(username: String): UserProfileResponse {
        return userService.getUserProfile(username)
    }
    override suspend fun getUserStats(): UserStats {
        val username = getLoggedInUsername()
        return userService.getUserStats(username)
    }
}