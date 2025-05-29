package pl.tablehub.mobile.client.model

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String,
    val nickname: String
)
