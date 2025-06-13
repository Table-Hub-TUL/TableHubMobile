package pl.tablehub.mobile.client.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.tablehub.mobile.util.Constants.BACKEND_IP
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://${BACKEND_IP}"

    val client: Retrofit
        get() {
            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(retryInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
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