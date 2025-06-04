package pl.tablehub.mobile.processing.mock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Position
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.Table
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.websocket.MessageHeader
import pl.tablehub.mobile.model.websocket.MessageType
import pl.tablehub.mobile.model.websocket.RestaurantResponseDTO
import pl.tablehub.mobile.model.websocket.WebSocketMessage
import pl.tablehub.mobile.processing.interfaces.IWebsocketMessageProcessor
import pl.tablehub.mobile.repository.IRestaurantsRepository
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class MockWebsocketMessageProcessor @Inject constructor(
    private val restaurantsRepository: IRestaurantsRepository
) : IWebsocketMessageProcessor {
    private val processorScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun start() {
        processorScope.launch {
            //process(stubWebSocketErrorMessage)
        }
    }

    override suspend fun process(webSocketMessage: WebSocketMessage) {
        Mocks.mockRestaurants.forEach {
            restaurantsRepository.addOrUpdateRestaurantFromDTO(it, Mocks.mockSections)
        }
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}


//val stubWebSocketErrorMessage: WebSocketMessage = WebSocketMessage(
//    header = MessageHeader(
//        messageId = UUID.randomUUID().toString(),
//        correlationId = null,
//        sender = "stub-sender",
//        type = MessageType.ERROR_RESPONSE, // Or any appropriate default/stub type
//        accessToken = null,
//        timestamp = System.currentTimeMillis()
//    ),
//    body = JsonElement("{}")
//)

object Mocks {
    private val lodzLatitude = 51.759445
    private val lodzLongitude = 19.457216

    val mockRestaurants = listOf(
        RestaurantResponseDTO(
            id = 1L,
            name = "Pierogarnia Łódzka",
            address = "Piotrkowska 100, 90-001 Łódź",
            location = Location(longitude = lodzLongitude + Random.nextDouble(-0.05, 0.05), latitude = lodzLatitude + Random.nextDouble(-0.05, 0.05)),
            cuisine = listOf("Polish", "Pierogi"),
            rating = 4.5,
        ),
        RestaurantResponseDTO(
            id = 2L,
            name = "Anatewka Manufaktura",
            address = "Drewnowska 58, 91-002 Łódź",
            location = Location(longitude = lodzLongitude + Random.nextDouble(-0.05, 0.05), latitude = lodzLatitude + Random.nextDouble(-0.05, 0.05)),
            cuisine = listOf("Jewish", "Polish"),
            rating = 4.8,
        ),
        RestaurantResponseDTO(
            id = 3L,
            name = "Cesky Film",
            address = "Tymienieckiego 3, 90-365 Łódź",
            location = Location(longitude = lodzLongitude + Random.nextDouble(-0.05, 0.05), latitude = lodzLatitude + Random.nextDouble(-0.05, 0.05)),
            cuisine = listOf("Czech", "European"),
            rating = 3.2,
        )
    )

    val mockTables = listOf(
        Table(id = 101L, position = Position(x = 2.0, y = 2.0), capacity = 6, status = TableStatus.AVAILABLE),
        Table(id = 102L, position = Position(x = 10.0, y = 10.0), capacity = 2, status = TableStatus.OCCUPIED),
        Table(id = 103L, position = Position(x = 20.0, y = 20.0), capacity = 6, status = TableStatus.AVAILABLE),
        Table(id = 201L, position = Position(x = 5.00, y = 10.0), capacity = 2, status = TableStatus.AVAILABLE),
        Table(id = 202L, position = Position(x = 20.00, y = 15.0), capacity = 4, status = TableStatus.OCCUPIED),
        Table(id = 301L, position = Position(x = 30.0, y = 30.0), capacity = 3, status = TableStatus.AVAILABLE),
    )

    val mockSections = listOf(
        Section(id = 11L, name = "Main Hall", tables = mockTables.filter { it.id.toString().startsWith("1") }),
        Section(id = 12L, name = "Patio", tables = mockTables.filter { it.id.toString().startsWith("2") }),
        Section(id = 13L, name = "VIP Room", tables = mockTables.filter { it.id.toString().startsWith("3") }),
    )
}