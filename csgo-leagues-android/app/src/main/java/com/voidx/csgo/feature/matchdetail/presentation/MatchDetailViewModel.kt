package com.voidx.csgo.feature.matchdetail.presentation

import com.voidx.csgo.core.arch.EffectViewModel
import com.voidx.csgo.feature.matchdetail.business.MatchDetailInteractor
import kotlin.coroutines.CoroutineContext

class MatchDetailViewModel(
    interactor: MatchDetailInteractor,
    reducer: MatchDetailReducer,
    runContext: CoroutineContext,
) : EffectViewModel<MatchDetailCommand, MatchDetailEffect, MatchDetailReducer, MatchDetailState>(
    runContext = runContext,
    interactor = interactor,
    reducer = reducer,
)