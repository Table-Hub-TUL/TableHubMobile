package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScopeProvider {
    @Provides
    @Singleton
    fun provideIOScope() : CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}