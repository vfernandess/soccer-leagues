package com.voidx.csgo.feature.matches

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.feature.matches.ui.MatchesScreen

const val MATCHES_ROUTE = "matches"

fun NavController.navigateToMatches() {
    navigate(MATCHES_ROUTE)
}

fun NavGraphBuilder.matchesScreen(
    onNavigateToDetail: (Match) -> Unit,
) {
    composable(route = MATCHES_ROUTE) {
        MatchesScreen(onNavigateToDetail = onNavigateToDetail)
    }
}
