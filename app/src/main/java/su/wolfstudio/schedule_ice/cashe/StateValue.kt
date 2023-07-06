package su.wolfstudio.schedule_ice.cashe

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal fun <T : Any> stateValue(initialValue: T) = lazy { StateValue(initialValue) }

open class StateValue<T>(
    initValue: T,
    private val state: MutableStateFlow<T> = MutableStateFlow(initValue)
) : StateFlow<T> by state {

    open suspend fun emit(value: T) {
        state.emit(value)
    }

    fun compareAndSet(expect: T, update: T): Boolean {
        return state.compareAndSet(expect, update)
    }

}

inline fun <T> StateValue<T>.update(function: (T) -> T) {
    while (true) {
        val prevValue = value
        val nextValue = function(prevValue)
        if (compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}