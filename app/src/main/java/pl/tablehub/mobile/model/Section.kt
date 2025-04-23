package pl.tablehub.mobile.model

data class Section(
    val id: Long,
    val name: String,
    val tables: List<Table>
)
