package pl.tablehub.mobile.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import pl.tablehub.mobile.client.model.auth.RefreshTokenRequest
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.datastore.EncryptedDataStore
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class AuthRepository @Inject constructor(
    private val encryptedDataStore: EncryptedDataStore,
    private val authServiceProvider: Provider<IAuthService>
) {

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private const val REWARD_TIMER_KEY_PREFIX = "reward_timer_"
    }

    suspend fun storeTokens(accessToken: String, refreshToken: String) {
        encryptedDataStore.put(JWT_TOKEN_KEY, accessToken)
        encryptedDataStore.put(REFRESH_TOKEN_KEY, refreshToken)
    }

    fun getJWT(): Flow<String?> = encryptedDataStore.get(JWT_TOKEN_KEY)

    fun getRefreshToken(): Flow<String?> = encryptedDataStore.get(REFRESH_TOKEN_KEY)

    suspend fun storeJWT(jwt: String) {
        encryptedDataStore.put(JWT_TOKEN_KEY, jwt)
    }



    suspend fun clearData() {
        encryptedDataStore.remove(JWT_TOKEN_KEY)
        encryptedDataStore.remove(REFRESH_TOKEN_KEY)
    }

    suspend fun hasValidToken(): Boolean {
        val jwt = getJWT().first()
        return jwt?.let { isTokenValid(it) } ?: false
    }

    suspend fun hasRefreshToken(): Boolean {
        return getRefreshToken().first() != null
    }

    suspend fun attemptRefreshToken(): Boolean {
        val refreshToken = getRefreshToken().first() ?: return false

        return try {
            val service = authServiceProvider.get()
            val response = service.refreshToken(RefreshTokenRequest(refreshToken))

            if (response.isSuccessful && response.body() != null) {
                val newTokens = response.body()!!
                storeTokens(newTokens.token, newTokens.refreshToken)
                true
            } else {
                clearData()
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            clearData()
            false
        }
    }

    private fun isTokenValid(token: String): Boolean {
        return try {
            val exp = getExpirationFromJWT(token)
            exp > (System.currentTimeMillis() / 1000)
        } catch (e: Exception) {
            false
        }
    }

    @Throws(IllegalArgumentException::class)
    fun getExpirationFromJWT(jwt: String): Long {
        val parts = jwt.split('.')
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT structure")
        }

        val payload = parts[1]
        // Use android.util.Base64 with URL_SAFE | NO_WRAP flags for JWTs
        val decodedBytes = android.util.Base64.decode(
            payload,
            android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
        )

        val payloadJson = String(decodedBytes, StandardCharsets.UTF_8)
        val jsonObject = JSONObject(payloadJson)
        return jsonObject.getLong("exp")
    }

    private fun getRewardTimerKey(rewardId: Long) =
        stringPreferencesKey("$REWARD_TIMER_KEY_PREFIX$rewardId")

    suspend fun saveRewardTimer(rewardId: Long, expiryTimestamp: Long) {
        encryptedDataStore.put(getRewardTimerKey(rewardId), expiryTimestamp)
    }

    fun getRewardTimer(rewardId: Long): Flow<Long?> {
        return encryptedDataStore.getLong(getRewardTimerKey(rewardId))
    }

    suspend fun removeRewardTimer(rewardId: Long) {
        encryptedDataStore.remove(getRewardTimerKey(rewardId))
    }
}