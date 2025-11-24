package pl.tablehub.mobile.model.v2

data class UserProfile(
    val fullName: String = "John Doe",
    val email: String = "john.doe@example.com",
    val points: Int = 650
)