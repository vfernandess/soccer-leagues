package com.voidx.csgo.feature.splash.business

import com.voidx.csgo.core.arch.SideEffectInteractor
import com.voidx.csgo.feature.splash.presentation.SplashCommand
import com.voidx.csgo.feature.splash.presentation.SplashEffect
import com.voidx.csgo.feature.splash.presentation.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SplashInteractor :
    SideEffectInteractor<SplashState, SplashCommand, SplashEffect, Nothing>() {

    override suspend fun invoke(
        state: SplashState,
        command: SplashCommand,
    ): Flow<SplashEffect> = when (command) {
        SplashCommand.Start -> flow {
            if (!state.isVisible) {
                return@flow
            }
            delay(SPLASH_DELAY_MILLIS)
            emit(SplashEffect.TimerCompleted)
        }
    }

    private companion object {
        const val SPLASH_DELAY_MILLIS = 2_000L
    }
}
