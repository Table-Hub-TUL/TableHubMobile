package pl.tablehub.mobile.model.websocket

data class Error(
    val errorCode: String,
    val errorMessage: String
) : MessageBody
