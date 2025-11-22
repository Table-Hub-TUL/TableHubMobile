package pl.tablehub.mobile.datastore

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class EncryptedDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val DATASTORE_NAME = "encrypted_preferences"
        private const val KEY_ALIAS = "DataStoreSecretKey"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 16


        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private const val REWARD_TIMER_KEY_PREFIX = "reward_timer_"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATASTORE_NAME
    )

    init {
        generateKey()
    }

    suspend fun storeJWT(jwt: String) {
        val encryptedJwt = encrypt(jwt)
        context.dataStore.edit { prefs ->
            prefs[JWT_TOKEN_KEY] = encryptedJwt
        }
    }

    fun getJWT(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[JWT_TOKEN_KEY]?.let { encryptedJwt ->
                decrypt(encryptedJwt)
            }
        }
    }

    suspend fun hasValidToken(): Boolean {
        val jwt = getJWT().first()
        return jwt?.let { isTokenValid(it) } ?: false
    }

    suspend fun clearJWT() {
        context.dataStore.edit { prefs ->
            prefs.remove(JWT_TOKEN_KEY)
        }
    }

    private fun isTokenValid(token: String): Boolean {
        val exp = getExpirationFromJWT(token)
        return exp > (System.currentTimeMillis() / 1_000)
    }

    @Throws(Exception::class)
    fun getExpirationFromJWT(jwt: String): Long {
        val parts = jwt.split('.')
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT structure: must have 3 parts. Found ${parts.size} parts.")
        }
        val payloadBase64Url = parts[1]
        val decodedBytes = try {
            Base64.decode(payloadBase64Url, Base64.URL_SAFE or Base64.NO_WRAP)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid Base64 encoding in JWT payload.", e)
        }
        val payloadJson = String(decodedBytes, StandardCharsets.UTF_8)
        val jsonObject = try {
            JSONObject(payloadJson)
        } catch (e: org.json.JSONException) {
            throw IllegalArgumentException("Invalid JSON format in JWT payload.", e)
        }
        try {
            return jsonObject.getLong("exp")
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to retrieve exp")
        }
    }

    private fun decrypt(encryptedText: String): String {
        try {
            val secretKey = getSecretKey()
            val combined = Base64.decode(encryptedText, Base64.DEFAULT)
            val iv = combined.sliceArray(0 until GCM_IV_LENGTH)
            val encryptedData = combined.sliceArray(GCM_IV_LENGTH until combined.size)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
            val decryptedBytes = cipher.doFinal(encryptedData)
            return String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            throw SecurityException("Decryption failed", e)
        }
    }

    private fun encrypt(plainText: String): String {
        try {
            val secretKey = getSecretKey()
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val iv = ByteArray(GCM_IV_LENGTH)
            SecureRandom().nextBytes(iv)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)
            val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
            val combined = iv + encryptedBytes
            return Base64.encodeToString(combined, Base64.DEFAULT)
        } catch (e: Exception) {
            throw SecurityException("Encryption failed", e)
        }
    }

    private fun generateKey() {
        try {
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
            keyStore.load(null)
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)

                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(false)
                    .build()

                keyGenerator.init(keyGenParameterSpec)
                keyGenerator.generateKey()
            }
        } catch (e: Exception) {
            throw SecurityException("Key generation failed", e)
        }
    }

    private fun getSecretKey(): SecretKey {
        return try {
            val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
            keyStore.load(null)
            keyStore.getKey(KEY_ALIAS, null) as SecretKey
        } catch (e: Exception) {
            throw SecurityException("Failed to retrieve secret key", e)
        }
    }


    private fun getRewardTimerKey(rewardId: Long) =
        stringPreferencesKey("$REWARD_TIMER_KEY_PREFIX$rewardId")

    suspend fun saveRewardTimer(rewardId: Long, expiryTimestamp: Long) {
        try {
            val encryptedTimestamp = encrypt(expiryTimestamp.toString())
            context.dataStore.edit { prefs ->
                prefs[getRewardTimerKey(rewardId)] = encryptedTimestamp
            }
        } catch (e: Exception) {
            throw SecurityException("Failed to save reward timer", e)
        }
    }

    fun getRewardTimer(rewardId: Long): Flow<Long?> {
        return context.dataStore.data.map { prefs ->
            prefs[getRewardTimerKey(rewardId)]?.let { encryptedTimestamp ->
                try {
                    decrypt(encryptedTimestamp).toLongOrNull()
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    suspend fun removeRewardTimer(rewardId: Long) {
        context.dataStore.edit { prefs ->
            prefs.remove(getRewardTimerKey(rewardId))
        }
    }
}