package pl.tablehub.mobile.providers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.security.CryptoManager
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "encrypted_preferences")

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvider {

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager {
        return CryptoManager()
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideEncryptedDataStore(
        dataStore: DataStore<Preferences>,
        cryptoManager: CryptoManager
    ): EncryptedDataStore {
        return EncryptedDataStore(dataStore, cryptoManager)
    }
}