package pl.tablehub.mobile.client.rest.interfaces

import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IRestaurantService {
    @GET("api/restaurants")
    suspend fun fetchRestaurants(
        @QueryMap options: Map<String, @JvmSuppressWildcards Any>
    ) : List<RestaurantListItem>

    @GET("api/restaurants/{id}")
    suspend fun fetchRestaurant(
        @Path("id") id: Long
    ): RestaurantDetail

    @POST("api/table/update-status")
    suspend fun updateTableStatus(@Body statusRequest: TableStatusChange)
}