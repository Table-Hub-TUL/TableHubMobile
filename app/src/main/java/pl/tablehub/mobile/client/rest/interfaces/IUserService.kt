package pl.tablehub.mobile.client.rest.interfaces

import pl.tablehub.mobile.client.model.user.UserStats
import pl.tablehub.mobile.client.rest.utils.Prefixes.API_USER_PREFIX
import retrofit2.http.GET
import retrofit2.http.Path

interface IUserService {
    @GET("${API_USER_PREFIX}/{username}/stats")
    suspend fun getUserStats(@Path("username") username: String) : UserStats
}