package pl.tablehub.mobile.providers

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.datastore.EncryptedDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvider {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): EncryptedDataStore {
        return EncryptedDataStore(context)
    }
}