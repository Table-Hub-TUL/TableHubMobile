package pl.tablehub.mobile.model.v2

data class Section(
    val id: Long,
    val name: String,
    val tables: List<Table>,
    val pois: List<POI>,
    val sectionLayout: SectionLayout
)
