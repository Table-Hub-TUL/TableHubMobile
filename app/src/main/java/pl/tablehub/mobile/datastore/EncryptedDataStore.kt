package pl.tablehub.mobile.datastore

import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.tablehub.mobile.security.CryptoManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val cryptoManager: CryptoManager
) {

    suspend fun <T> put(key: Preferences.Key<String>, value: T) {
        val stringValue = value.toString() // Convert any type to string first
        val encryptedBytes = cryptoManager.encrypt(stringValue)
        val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)

        dataStore.edit { prefs ->
            prefs[key] = encryptedBase64
        }
    }

    fun get(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[key]?.let { encryptedBase64 ->
                try {
                    val encryptedBytes = Base64.decode(encryptedBase64, Base64.NO_WRAP)
                    cryptoManager.decrypt(encryptedBytes)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    suspend fun remove(key: Preferences.Key<String>) {
        dataStore.edit { prefs ->
            prefs.remove(key)
        }
    }
    fun getLong(key: Preferences.Key<String>): Flow<Long?> {
        return get(key).map { it?.toLongOrNull() }
    }
}