package pl.tablehub.mobile.client.rest.interfaces

import pl.tablehub.mobile.client.model.TableStatusChange
import pl.tablehub.mobile.model.Restaurant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IRestaurantService {
    @GET("api/restaurants")
    suspend fun fetchRestaurants() : List<Restaurant>

    @POST("api/table/update-status")
    suspend fun updateTableStatus(@Body statusRequest: TableStatusChange)
}