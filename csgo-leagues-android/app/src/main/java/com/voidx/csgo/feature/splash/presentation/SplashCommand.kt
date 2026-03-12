package com.voidx.csgo.feature.splash.presentation

sealed interface SplashCommand {
    data object Start : SplashCommand
}
