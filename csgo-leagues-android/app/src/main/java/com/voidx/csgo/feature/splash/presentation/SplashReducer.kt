package com.voidx.csgo.feature.splash.presentation

import com.voidx.csgo.core.arch.SimpleReducer

class SplashReducer : SimpleReducer<SplashState, SplashEffect>(SplashState()) {
    override fun reduce(effect: SplashEffect, currentState: SplashState): SplashState =
        when (effect) {
            SplashEffect.TimerCompleted -> currentState.copy(isVisible = false)
        }
}
