package com.voidx.csgo.feature.matchdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.voidx.csgo.core.theme.AppBackground
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.feature.matchdetail.ui.MatchDetailScreen
import com.voidx.csgo.navigation.MatchNavType

private const val MATCH_DETAIL_ROUTE = "matchDetail"
private const val MATCH_ARG = "match"

fun NavController.navigateToMatchDetail(match: Match) {
    navigate("$MATCH_DETAIL_ROUTE/${MatchNavType.serializeAsValue(match)}")
}

fun NavGraphBuilder.matchDetailScreen(
    onBack: () -> Unit,
    onRecover: () -> Unit,
) {
    composable(
        route = "$MATCH_DETAIL_ROUTE/{$MATCH_ARG}",
        arguments = listOf(
            navArgument(MATCH_ARG) {
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        val match = MatchNavType.deserializeOrNull(
            backStackEntry.arguments?.getString(MATCH_ARG),
        )

        if (match == null) {
            InvalidMatchDetailRoute(onRecover = onRecover)
        } else {
            MatchDetailScreen(
                match = match,
                onBack = onBack,
            )
        }
    }
}

@Composable
private fun InvalidMatchDetailRoute(onRecover: () -> Unit) {
    LaunchedEffect(Unit) {
        onRecover()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}
