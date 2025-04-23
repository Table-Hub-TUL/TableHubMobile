package pl.tablehub.mobile.model

data class Table(
    val id: Long,
    val position: Position,
    val capacity: Int,
    val status: TableStatus
)
