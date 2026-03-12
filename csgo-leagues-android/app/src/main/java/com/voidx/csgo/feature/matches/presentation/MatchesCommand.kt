package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.domain.entity.Match

sealed interface MatchesCommand {
    data object Load : MatchesCommand
    data object Refresh : MatchesCommand
    data object LoadNextPage : MatchesCommand
    data object Retry : MatchesCommand
    data class MatchTapped(val match: Match) : MatchesCommand
}
