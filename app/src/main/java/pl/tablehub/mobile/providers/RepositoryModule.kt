package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.repository.IRestaurantsRepository
import pl.tablehub.mobile.repository.RestaurantsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindRestaurantRepository(): IRestaurantsRepository {
        return RestaurantsRepositoryImpl()
    }
}