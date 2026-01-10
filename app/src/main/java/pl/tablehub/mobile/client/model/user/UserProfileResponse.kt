package pl.tablehub.mobile.client.model.user

import java.time.OffsetDateTime

data class UserProfileResponse(
    val id: Long,
    val userName: String,
    val email: String,
    val name: String?,
    val points: Int,
    val roles: Set<String>,
    val registeredAt: String?
)