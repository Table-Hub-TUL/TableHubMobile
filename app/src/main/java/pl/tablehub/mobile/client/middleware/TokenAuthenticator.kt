package pl.tablehub.mobile.client.middleware

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import pl.tablehub.mobile.client.model.auth.RefreshTokenRequest
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val authServiceProvider: Provider<IAuthService>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.url.pathSegments.last() == "refresh") {
            return null
        }
        val tokenFromRequest = response.request.header("Authorization")?.substringAfter("Bearer ")
        synchronized(this) {
            val currentToken = runBlocking { authRepository.getJWT().first() }
            if (currentToken != null && tokenFromRequest != currentToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }
            val refreshToken = runBlocking { authRepository.getRefreshToken().first() } ?: return null
            val newTokens = try {
                val api = authServiceProvider.get()
                val call = runBlocking {
                    api.refreshToken(RefreshTokenRequest(refreshToken))
                }
                if (call.isSuccessful && call.body() != null) {
                    call.body()!!
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
            return if (newTokens != null) {
                runBlocking {
                    authRepository.storeTokens(newTokens.token, newTokens.refreshToken)
                }
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newTokens.token}")
                    .build()
            } else {
                runBlocking { authRepository.clearData() }
                null
            }
        }
    }
}