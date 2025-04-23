package pl.tablehub.mobile.services.mock

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Position
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.Table
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionInitialState
import pl.tablehub.mobile.model.websocket.RestaurantSubscriptionResponse
import pl.tablehub.mobile.model.websocket.RestaurantsRequest
import pl.tablehub.mobile.model.websocket.RestaurantsResponse
import pl.tablehub.mobile.model.websocket.TableUpdateRequest
import pl.tablehub.mobile.model.websocket.TableUpdateResponse
import pl.tablehub.mobile.services.interfaces.TablesService
import kotlin.random.Random

@AndroidEntryPoint
class MockTableService : Service(), TablesService {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val lodzLatitude = 51.759445
    private val lodzLongitude = 19.457216

    private val mockRestaurants = listOf(
        Restaurant(
            id = 1L,
            name = "Pierogarnia Łódzka",
            address = "Piotrkowska 100, 90-001 Łódź",
            location = Location(longitude = lodzLongitude + Random.nextDouble(-0.05, 0.05), latitude = lodzLatitude + Random.nextDouble(-0.05, 0.05)),
            cuisine = listOf("Polish", "Pierogi"),
            rating = 4.5,
        ),
        Restaurant(
            id = 2L,
            name = "Anatewka Manufaktura",
            address = "Drewnowska 58, 91-002 Łódź",
            location = Location(longitude = lodzLongitude + Random.nextDouble(-0.05, 0.05), latitude = lodzLatitude + Random.nextDouble(-0.05, 0.05)),
            cuisine = listOf("Jewish", "Polish"),
            rating = 4.8,
        ),
        Restaurant(
            id = 3L,
            name = "Cesky Film",
            address = "Tymienieckiego 3, 90-365 Łódź",
            location = Location(longitude = lodzLongitude + Random.nextDouble(-0.05, 0.05), latitude = lodzLatitude + Random.nextDouble(-0.05, 0.05)),
            cuisine = listOf("Czech", "European"),
            rating = 4.2,
        )
    )

    private val mockTables = listOf(
        Table(id = 101L, position = Position(x = 10.0, y = 20.0), capacity = 4, status = TableStatus.AVAILABLE),
        Table(id = 102L, position = Position(x = 15.0, y = 25.0), capacity = 2, status = TableStatus.OCCUPIED),
        Table(id = 103L, position = Position(x = 20.0, y = 30.0), capacity = 6, status = TableStatus.AVAILABLE),
        Table(id = 201L, position = Position(x = 5.0, y = 10.0), capacity = 2, status = TableStatus.AVAILABLE),
        Table(id = 202L, position = Position(x = 8.0, y = 15.0), capacity = 4, status = TableStatus.OCCUPIED),
        Table(id = 301L, position = Position(x = 30.0, y = 10.0), capacity = 8, status = TableStatus.AVAILABLE),
    )

    private val mockSections = listOf(
        Section(id = 11L, name = "Main Hall", tables = mockTables.filter { it.id.toString().startsWith("1") }),
        Section(id = 12L, name = "Patio", tables = mockTables.filter { it.id.toString().startsWith("2") }),
        Section(id = 13L, name = "VIP Room", tables = mockTables.filter { it.id.toString().startsWith("3") }),
    )

    override fun requestRestaurants(requestParams: RestaurantsRequest): RestaurantsResponse {
        println("MockTableService: requestRestaurants called with $requestParams")
        return RestaurantsResponse(requestParams, mockRestaurants)
    }

    override fun subscribeRestaurants(requestParams: List<Restaurant>): List<RestaurantSubscriptionResponse> {
        println("MockTableService: subscribeRestaurants called for ${requestParams.map { it.name }}")
        return requestParams.map { restaurant ->
            RestaurantSubscriptionResponse(
                restaurantId = restaurant.id,
                message = "Success",
                success = true,
                initialState = RestaurantSubscriptionInitialState(
                    id = restaurant.id,
                    section = mockSections,
                )
            )
        }
    }

    override fun unSubscribeRestaurants(requestParams: List<Restaurant>): Error? {
        println("MockTableService: unSubscribeRestaurants called for ${requestParams.map { it.name }}")
        return null
    }

    override fun updateTableStatus(requestParams: List<TableUpdateRequest>): List<TableUpdateResponse> {
        println("MockTableService: updateTableStatus called with $requestParams")
        val responses = requestParams.map { req ->
            TableUpdateResponse(
                restaurantId = req.restaurantId,
                tableId = req.tableId,
                resultingStatus = req.requestedStatus,
                updateSuccess = true,
                message = "Status updated successfully (mock)",
                sectionId = req.sectionId,
                pointsAwarded = 1
            )
        }
        return responses
    }
}