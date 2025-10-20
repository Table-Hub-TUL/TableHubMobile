package pl.tablehub.mobile.client.rest

import pl.tablehub.mobile.client.middleware.AuthMiddleware
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.tablehub.mobile.util.Constants.BACKEND_REST_IP
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val BASE_URL = BACKEND_REST_IP

    val client: Retrofit
        get() {
            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(AuthMiddleware())
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}
