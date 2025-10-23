package pl.tablehub.mobile.client.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String,
    val nickname: String
)
