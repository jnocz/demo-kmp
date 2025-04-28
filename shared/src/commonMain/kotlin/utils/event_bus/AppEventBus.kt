package utils.event_bus


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AppEventBus {
    private val _eventFlow = MutableSharedFlow<AppEvent>()

    fun subscribe(scope: CoroutineScope, block: suspend (AppEvent) -> Unit) = _eventFlow.onEach(block).launchIn(scope)
    suspend fun emit(appEvent: AppEvent) = _eventFlow.emit(appEvent)
}

sealed class AppEvent {
    data class LogoutEvent(val message: String) : AppEvent()
    object EventWithoutData : AppEvent()
}