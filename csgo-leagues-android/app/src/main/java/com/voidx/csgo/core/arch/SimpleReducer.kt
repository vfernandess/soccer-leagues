package com.voidx.csgo.core.arch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class SimpleReducer<State, Effect>(
    initialState: State
): Reducer<State, Effect> {
    private val mutableState = MutableStateFlow(initialState)

    override val state: StateFlow<State>
        get() = mutableState

    override fun invoke(effect: Effect) {
        val result = reduce(effect, state.value)

        mutableState.tryEmit(result)
    }

    abstract fun reduce(effect: Effect, currentState: State): State
}
