package com.voidx.csgo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.voidx.csgo.feature.matchdetail.matchDetailScreen
import com.voidx.csgo.feature.matchdetail.navigateToMatchDetail
import com.voidx.csgo.feature.matches.MATCHES_ROUTE
import com.voidx.csgo.feature.matches.matchesScreen
import com.voidx.csgo.feature.splash.SPLASH_ROUTE
import com.voidx.csgo.feature.splash.splashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
    ) {
        splashScreen(
            onNavigateToMatches = {
                navController.navigate(MATCHES_ROUTE) {
                    popUpTo(SPLASH_ROUTE) { inclusive = true }
                    launchSingleTop = true
                }
            },
        )

        matchesScreen(onNavigateToDetail = navController::navigateToMatchDetail)

        matchDetailScreen(
            onBack = { navController.popBackStack() },
            onRecover = {
                val popped = navController.popBackStack()
                if (!popped) {
                    navController.navigate(MATCHES_ROUTE) {
                        popUpTo(SPLASH_ROUTE) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}
