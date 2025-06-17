package pl.tablehub.mobile.client.rest.interfaces

import retrofit2.Response
import pl.tablehub.mobile.client.model.LoginRequest
import pl.tablehub.mobile.client.model.LoginResponse
import pl.tablehub.mobile.client.model.SignUpRequest
import pl.tablehub.mobile.client.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("/auth/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/signup")
    suspend fun signupUser(@Body signupRequest: SignUpRequest): Response<SignUpResponse>
}