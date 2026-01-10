package pl.tablehub.mobile.client.model.user

data class UserStats(
    val email: String,
    val displayName: String,
    val points: Int,
    val reportsCount: Int,
    val ranking: Int
)
