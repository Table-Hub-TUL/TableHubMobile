package pl.tablehub.mobile.client

import pl.tablehub.mobile.client.model.LoginRequest
import pl.tablehub.mobile.client.model.LoginResponse
import pl.tablehub.mobile.client.model.SignUpRequest
import pl.tablehub.mobile.client.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthService {
    @POST("/auth/signin")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>?

    @POST("/auth/signup")
    fun signupUser(@Body signupRequest: SignUpRequest): Call<SignUpResponse>
}