package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.domain.entity.Match

data class MatchesState(
    val matches: List<Match> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = false,
    val error: MatchesError? = null
) {
    val contentPhase: MatchesContentPhase
        get() = when {
            isLoading && matches.isEmpty() -> MatchesContentPhase.Loading
            error != null && matches.isEmpty() -> MatchesContentPhase.Error(error)
            matches.isEmpty() -> MatchesContentPhase.Empty
            else -> MatchesContentPhase.Loaded
        }
}

sealed interface MatchesContentPhase {
    data object Loading : MatchesContentPhase
    data object Empty : MatchesContentPhase
    data object Loaded : MatchesContentPhase
    data class Error(val error: MatchesError) : MatchesContentPhase
}
