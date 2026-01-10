package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.client.rest.interfaces.IUserService
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.repository.IUserRepository
import pl.tablehub.mobile.repository.RestaurantsRepositoryImpl
import pl.tablehub.mobile.repository.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindRestaurantRepository(): IRestaurantsRepository {
        return RestaurantsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun bindUserRepository(
        userService: IUserService,
        dataStore: EncryptedDataStore
    ): IUserRepository {
        return UserRepositoryImpl(userService, dataStore)
    }
}