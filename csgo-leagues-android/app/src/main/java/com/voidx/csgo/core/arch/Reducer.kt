package com.voidx.csgo.core.arch

import kotlinx.coroutines.flow.StateFlow

interface Reducer<State, Effect> {

    val state: StateFlow<State>

    operator fun invoke(effect: Effect)
}
