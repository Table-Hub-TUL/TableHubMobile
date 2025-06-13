package pl.tablehub.mobile.client.rest.config

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws


class RetryInterceptor(private val retries: Int = 3) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response: Response? = null
        var tryCount = 0
        var exception: Exception? = null

        while (tryCount < retries && (response == null || !response.isSuccessful)) {
            try {
                response?.close()
                response = chain.proceed(request)
            } catch (e: IOException) {
                exception = e
            }
            tryCount++
        }
        if (response == null && exception != null) {
            throw exception
        }

        return response!!
    }
}