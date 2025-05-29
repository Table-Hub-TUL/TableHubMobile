package pl.tablehub.mobile.client.model

data class LoginResponse(
    val token: String,
    val username: String,
    val authorities: List<Authority>,
    val type: String
) {
    data class Authority(
        val authority: String
    )
}
