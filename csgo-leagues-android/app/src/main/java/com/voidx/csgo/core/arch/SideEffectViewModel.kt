package com.voidx.csgo.core.arch

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlin.coroutines.CoroutineContext
import com.voidx.csgo.core.arch.Reducer as ArchReducer

open class SideEffectViewModel<Command, Effect, Reducer, State, ViewSideEffect>(
    runContext: CoroutineContext,
    private val interactor: SideEffectInteractor<State, Command, Effect, ViewSideEffect>,
    sideEffectReducer: ArchReducer<State, Effect>
) : EffectViewModel<Command, Effect, Reducer, State>(
    runContext,
    interactor,
    sideEffectReducer
) {
    val sideEffect: SharedFlow<ViewSideEffect> =
        interactor.sideEffect.shareIn(
            viewModelScope,
            SharingStarted.Lazily
        )
}
