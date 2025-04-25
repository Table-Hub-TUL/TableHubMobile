package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.services.interfaces.TablesService
import pl.tablehub.mobile.services.mock.MockTableService
import pl.tablehub.mobile.services.websocket.WebSocketService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideTablesService() : TablesService {
        return MockTableService()
    }
}