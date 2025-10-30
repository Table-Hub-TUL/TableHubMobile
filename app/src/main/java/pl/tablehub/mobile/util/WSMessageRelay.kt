package pl.tablehub.mobile.util

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WSMessageRelay @Inject constructor() {
    private val _messageAggregateFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val _messageSpecificFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val messageAggregateFlow = _messageAggregateFlow.asSharedFlow()
    val messageSpecificFlow = _messageSpecificFlow.asSharedFlow()

    suspend fun emitMessageAggregate(message: String) {
        _messageAggregateFlow.emit(message)
    }

    suspend fun emitMessageSpecific(message: String) {
        _messageSpecificFlow.emit(message)
    }
}