package pl.tablehub.mobile.model.websocket

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val errorCode: String,
    val errorMessage: String
) : MessageBody
