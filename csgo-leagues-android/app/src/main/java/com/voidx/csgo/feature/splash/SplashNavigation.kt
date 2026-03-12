package com.voidx.csgo.feature.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.voidx.csgo.feature.splash.ui.SplashScreen

const val SPLASH_ROUTE = "splash"

fun NavGraphBuilder.splashScreen(
    onNavigateToMatches: () -> Unit,
) {
    composable(route = SPLASH_ROUTE) {
        SplashScreen(onNavigateToMatches = onNavigateToMatches)
    }
}
