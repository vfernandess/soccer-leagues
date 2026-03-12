package com.voidx.csgo.feature.splash.presentation

import com.voidx.csgo.core.arch.SideEffectViewModel
import com.voidx.csgo.feature.splash.business.SplashInteractor
import kotlin.coroutines.CoroutineContext

class SplashViewModel(
    interactor: SplashInteractor,
    reducer: SplashReducer,
    runContext: CoroutineContext,
) : SideEffectViewModel<SplashCommand, SplashEffect, SplashReducer, SplashState, Nothing>(
    runContext = runContext,
    interactor = interactor,
    sideEffectReducer = reducer,
)
