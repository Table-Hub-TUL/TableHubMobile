package pl.tablehub.mobile.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WSMessageRelay @Inject constructor() {
    private val _messageFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val messageFlow = _messageFlow.asSharedFlow()

    suspend fun emitMessage(message: String) {
        _messageFlow.emit(message)
    }
}