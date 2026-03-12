package com.voidx.csgo.core.arch

import kotlinx.coroutines.flow.Flow

interface Interactor<State, Command, Effect> {

    suspend operator fun invoke(state: State, command: Command): Flow<Effect>
}
