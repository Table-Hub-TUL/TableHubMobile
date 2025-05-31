package pl.tablehub.mobile.client.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val username: String,
    val authorities: List<Authority>,
    val type: String
) {
    @Serializable
    data class Authority(
        val authority: String
    )
}
