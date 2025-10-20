package pl.tablehub.mobile.model.v2

import pl.tablehub.mobile.model.TableStatus

data class Table(
    val id: Long,
    var tableStatus: TableStatus,
    val position: Point,
    val capacity: Int
)