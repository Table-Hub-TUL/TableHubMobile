package pl.tablehub.mobile.client.rest.interfaces

import pl.tablehub.mobile.client.model.user.UserStats
import pl.tablehub.mobile.client.rest.utils.Prefixes.API_USER_PREFIX
import pl.tablehub.mobile.model.v2.Reward
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface IUserService {
    @GET("${API_USER_PREFIX}/{username}/stats")
    suspend fun getUserStats(@Path("username") username: String) : UserStats
    @GET("${API_USER_PREFIX}/{username}/rewards}")
    suspend fun getUserRewards(@Path("username") username: String) : List<Reward>
    @DELETE("${API_USER_PREFIX}/{username}/rewards/{rewardID}")
    suspend fun redeemReward(@Path("username") username: String, @Path("rewardID") rewardID: Long)
}