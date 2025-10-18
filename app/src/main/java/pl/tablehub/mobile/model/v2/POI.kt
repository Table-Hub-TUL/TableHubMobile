package pl.tablehub.mobile.model.v2

data class POI(
    val description: String,
    val topLeft: Point,
    val bottomRight: Point,
)
