package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.domain.entity.Match

sealed interface MatchesEffect {
    data object Loading : MatchesEffect
    data object Refreshing : MatchesEffect
    data object LoadingMore : MatchesEffect
    data object NavigationRequested : MatchesEffect
    data class MatchesLoaded(
        val matches: List<Match>,
        val hasNext: Boolean
    ) : MatchesEffect

    data class PageLoaded(
        val matches: List<Match>,
        val hasNext: Boolean
    ) : MatchesEffect

    data class Error(val error: MatchesError) : MatchesEffect
    data object PaginationError : MatchesEffect
}
