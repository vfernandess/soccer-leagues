package com.voidx.csgo.core.arch

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class SideEffectInteractor<State, Command, Effect, SideEffect> :
    Interactor<State, Command, Effect> {

    private val _sideEffect: Channel<SideEffect> = Channel(Channel.UNLIMITED)
    val sideEffect: Flow<SideEffect> = _sideEffect.receiveAsFlow()

    protected infix fun Effect.sendSideEffect(viewSideEffect: SideEffect): Effect {
        _sideEffect.trySend(viewSideEffect)
        return this
    }
}
