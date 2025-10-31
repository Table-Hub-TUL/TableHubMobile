package pl.tablehub.mobile.model

import kotlinx.serialization.Serializable

@Serializable
enum class TableStatus {
    AVAILABLE,
    OCCUPIED,
    UNKNOWN
}