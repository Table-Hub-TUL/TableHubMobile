package pl.tablehub.mobile.client.rest.interfaces

import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.client.rest.utils.Prefixes.API_RESTAURANT_PREFIX
import pl.tablehub.mobile.client.rest.utils.Prefixes.TABLE_STATUS_PREFIX
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IRestaurantService {
    @GET(API_RESTAURANT_PREFIX)
    suspend fun fetchRestaurants(
        @QueryMap options: Map<String, @JvmSuppressWildcards Any>
    ) : List<RestaurantListItem>

    @GET("${API_RESTAURANT_PREFIX}/{id}")
    suspend fun fetchRestaurant(
        @Path("id") id: Long
    ): RestaurantDetail

    @POST("${TABLE_STATUS_PREFIX}/update-status")
    suspend fun updateTableStatus(@Body statusRequest: TableStatusChange)

    @GET("api/restaurants/cuisine-list")
    suspend fun fetchCuisineList(): List<String>
}