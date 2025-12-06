package pl.tablehub.mobile.client.rest.interfaces

import retrofit2.Response
import pl.tablehub.mobile.client.model.auth.LoginRequest
import pl.tablehub.mobile.client.model.auth.LoginResponse
import pl.tablehub.mobile.client.model.auth.RefreshTokenRequest
import pl.tablehub.mobile.client.model.auth.SignUpRequest
import pl.tablehub.mobile.client.model.auth.SignUpResponse
import pl.tablehub.mobile.client.rest.utils.Prefixes.AUTH_PREFIX
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {

    @POST("${AUTH_PREFIX}/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("${AUTH_PREFIX}/signup")
    suspend fun signupUser(@Body signupRequest: SignUpRequest): Response<SignUpResponse>

    @POST("${AUTH_PREFIX}/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<LoginResponse>
}