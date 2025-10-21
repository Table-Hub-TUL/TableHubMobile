package pl.tablehub.mobile.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.websocket.WebSocketClient
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import pl.tablehub.mobile.client.middleware.AuthMiddleware
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.client.rest.interfaces.IRestaurantService
import pl.tablehub.mobile.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {
    @Provides
    @Singleton
    fun provideWebSocketClient() : WebSocketClient {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(Duration.ofMinutes(1))
            .pingInterval(Duration.ofSeconds(90))
            .build()
        return OkHttpWebSocketClient(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authMiddleware: AuthMiddleware): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authMiddleware)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BACKEND_REST_IP)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): IAuthService {
        return retrofit.create(IAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideRestaurantService(retrofit: Retrofit): IRestaurantService {
        return retrofit.create(IRestaurantService::class.java)
    }
}