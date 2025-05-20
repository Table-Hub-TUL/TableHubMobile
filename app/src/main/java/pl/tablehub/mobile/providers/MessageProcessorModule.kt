package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.tablehub.mobile.processing.implementation.WebsocketMessageProcessorImpl
import pl.tablehub.mobile.processing.interfaces.IWebsocketMessageProcessor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageProcessorModule {
    @Provides
    @Singleton
    fun provideMessageProcessor(
        impl: WebsocketMessageProcessorImpl
    ): IWebsocketMessageProcessor = impl
}
