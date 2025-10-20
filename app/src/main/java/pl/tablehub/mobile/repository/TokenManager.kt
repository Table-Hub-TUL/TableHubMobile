package pl.tablehub.mobile.repository

object TokenManager {
    private var jwt: String? = null

    fun setToken(token: String?) {
        jwt = token
    }

    fun getToken(): String? {
        return jwt
    }
}