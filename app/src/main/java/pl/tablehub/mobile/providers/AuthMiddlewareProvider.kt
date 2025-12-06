package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.client.middleware.AuthMiddleware
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.repository.AuthRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthMiddlewareProvider {
    @Provides
    @Singleton
    fun provideAuthMiddleware(authRepository: AuthRepository) : AuthMiddleware {
        return AuthMiddleware(authRepository)
    }
}