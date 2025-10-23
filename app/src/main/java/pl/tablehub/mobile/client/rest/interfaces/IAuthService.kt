package pl.tablehub.mobile.client.rest.interfaces

import retrofit2.Response
import pl.tablehub.mobile.client.model.auth.LoginRequest
import pl.tablehub.mobile.client.model.auth.LoginResponse
import pl.tablehub.mobile.client.model.auth.SignUpRequest
import pl.tablehub.mobile.client.model.auth.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("/auth/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/signup")
    suspend fun signupUser(@Body signupRequest: SignUpRequest): Response<SignUpResponse>
}