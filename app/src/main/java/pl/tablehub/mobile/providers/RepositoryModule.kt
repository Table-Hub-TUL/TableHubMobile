package pl.tablehub.mobile.providers

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.repository.RestaurantsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRestaurantRepository(
        restaurantRepositoryImpl: RestaurantsRepositoryImpl
    ): IRestaurantsRepository
}