package com.voidx.csgo.feature.splash.presentation

sealed interface SplashEffect {
    data object TimerCompleted : SplashEffect
}
