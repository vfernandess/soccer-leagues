package com.voidx.csgo.core.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.voidx.csgo.core.arch.Reducer as ArchReducer

open class EffectViewModel<Command, Effect, Reducer, State>(
    private val runContext: CoroutineContext,
    private val interactor: Interactor<State, Command, Effect>,
    private val reducer: ArchReducer<State, Effect>
) : ViewModel(), FlowCollector<Effect> {

    private val _effect = Channel<Effect>()

    val effect = _effect.receiveAsFlow().shareIn(viewModelScope, SharingStarted.Lazily)

    val state: StateFlow<State>
        get() = reducer.state

    override suspend fun emit(value: Effect) {
        _effect.trySend(value)
        reducer.invoke(value)
    }

    private var coroutineJobs: List<Job> = mutableListOf()

    fun dispatch(command: Command) {
        coroutineJobs = coroutineJobs + viewModelScope.launch(context = runContext) {
            interactor
                .invoke(reducer.state.value, command)
                .collect(this@EffectViewModel)
        }
    }

    override fun onCleared() {
        coroutineJobs.forEach {
            if (it.isCompleted.not()) {
                it.cancel()
            }
        }
    }
}
