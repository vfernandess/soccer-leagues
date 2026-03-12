package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.core.arch.SideEffectViewModel
import com.voidx.csgo.feature.matches.business.MatchesInteractor
import kotlin.coroutines.CoroutineContext

class MatchesViewModel(
    interactor: MatchesInteractor,
    reducer: MatchesReducer,
    runContext: CoroutineContext,
) : SideEffectViewModel<MatchesCommand, MatchesEffect, MatchesReducer, MatchesState, MatchesSideEffect>(
    runContext = runContext,
    interactor = interactor,
    sideEffectReducer = reducer
)
