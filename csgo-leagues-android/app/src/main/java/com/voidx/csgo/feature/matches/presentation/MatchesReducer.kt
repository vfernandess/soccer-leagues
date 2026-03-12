package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.core.arch.SimpleReducer
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.MatchStatus
import java.time.LocalDateTime

class MatchesReducer : SimpleReducer<MatchesState, MatchesEffect>(MatchesState()) {

    override fun reduce(effect: MatchesEffect, currentState: MatchesState): MatchesState = when (effect) {
        MatchesEffect.Loading -> currentState.copy(
            isLoading = true,
            isRefreshing = false,
            isLoadingMore = false,
            error = null
        )

        MatchesEffect.Refreshing -> currentState.copy(
            isLoading = false,
            isRefreshing = true,
            isLoadingMore = false,
            error = null
        )

        MatchesEffect.LoadingMore -> currentState.copy(
            isLoadingMore = true
        )

        MatchesEffect.NavigationRequested -> currentState

        is MatchesEffect.MatchesLoaded -> currentState.copy(
            matches = sortMatches(effect.matches),
            isLoading = false,
            isRefreshing = false,
            isLoadingMore = false,
            currentPage = 1,
            hasMorePages = effect.hasNext,
            error = null
        )

        is MatchesEffect.PageLoaded -> currentState.copy(
            matches = sortMatches(deduplicateById(currentState.matches + effect.matches)),
            isLoadingMore = false,
            currentPage = currentState.currentPage + 1,
            hasMorePages = effect.hasNext
        )

        is MatchesEffect.Error -> currentState.copy(
            isLoading = false,
            isRefreshing = false,
            isLoadingMore = false,
            error = effect.error.takeIf { currentState.matches.isEmpty() }
        )

        MatchesEffect.PaginationError -> currentState.copy(
            isLoadingMore = false
        )
    }

    private fun sortMatches(matches: List<Match>): List<Match> =
        matches.sortedWith(
            compareBy<Match> { match ->
                when (match.status) {
                    MatchStatus.InProgress -> 0
                    MatchStatus.Scheduled -> 1
                    MatchStatus.Ended -> 2
                }
            }.thenBy { it.beginAt ?: LocalDateTime.MAX }
        )

    private fun deduplicateById(matches: List<Match>): List<Match> {
        val seen = mutableSetOf<Int>()
        return matches.filter { seen.add(it.id) }
    }
}
