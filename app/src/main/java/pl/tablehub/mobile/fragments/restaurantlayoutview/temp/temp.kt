package pl.tablehub.mobile.fragments.restaurantlayoutview.temp

import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.POI
import pl.tablehub.mobile.model.v2.Point
import pl.tablehub.mobile.model.v2.Section
import pl.tablehub.mobile.model.v2.SectionLayout
import pl.tablehub.mobile.model.v2.TableDetail

val exampleTableDetail = TableDetail(
    id = 1,
    tableStatus = TableStatus.OCCUPIED,
    position = Point(x = 100, y = 150),
    capacity = 9
)


val sampleSections = listOf(
    Section(
        id = 101,
        name = "Main Dining Hall",
        tableDetails = listOf(
            TableDetail(
                id = 1,
                tableStatus = TableStatus.OCCUPIED,
                position = Point(x = 160, y = 450),
                capacity = 4
            ),
            TableDetail(
                id = 2,
                tableStatus = TableStatus.OCCUPIED,
                position = Point(x = 450, y = 600),
                capacity = 2
            ),
            TableDetail(
                id = 3,
                tableStatus = TableStatus.AVAILABLE,
                position = Point(x = 680, y = 1000),
                capacity = 6
            )
        ),
        pois = listOf(
            POI(
                description = "Hostess Stand / Entrance",
                topLeft = Point(x = 50, y = 50),
                bottomRight = Point(x = 400, y = 350),
            )
        ),
        sectionLayout = SectionLayout(
            viewportWidth = 4000,
            viewportHeight = 3000,
            shape = "M0 3000 L0 0 L1500 0 L1500 1500 L4000 1500 L4000 3000 Z"
        )
    ),
    Section(
        id = 102,
        name = "Outdoor Patio",
        tableDetails = listOf(
            TableDetail(
                id = 4,
                tableStatus = TableStatus.AVAILABLE,
                position = Point(x = 60, y = 20),
                capacity = 4
            ),
            TableDetail(
                id = 5,
                tableStatus = TableStatus.AVAILABLE,
                position = Point(x = 150, y = 60),
                capacity = 2
            )
        ),
        pois = listOf(
            POI(
                description = "Washrooms Link",
                topLeft = Point(x = 230, y = 100),
                bottomRight = Point(x = 330, y = 180),
            )
        ),
        sectionLayout = SectionLayout(
            viewportWidth = 600,
            viewportHeight = 400,
            shape = "M0 0 L600 0 L600 400 L0 400 Z"
        )
    )
)