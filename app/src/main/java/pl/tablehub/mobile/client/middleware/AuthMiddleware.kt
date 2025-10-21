package pl.tablehub.mobile.client.middleware

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import pl.tablehub.mobile.datastore.EncryptedDataStore
import javax.inject.Inject

class AuthMiddleware @Inject constructor(private val encryptedPreferences: EncryptedDataStore): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val bearerToken = runBlocking(Dispatchers.IO) {
            encryptedPreferences.getJWT().first()
        }
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(newRequest)
    }
}