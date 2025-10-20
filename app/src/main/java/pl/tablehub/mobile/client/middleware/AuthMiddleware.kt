package pl.tablehub.mobile.client.middleware

import okhttp3.Interceptor
import okhttp3.Response
import pl.tablehub.mobile.repository.TokenManager

class AuthMiddleware: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val bearerToken = TokenManager.getToken()
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(newRequest)
    }
}