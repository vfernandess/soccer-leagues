package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.domain.entity.Match

sealed interface MatchesSideEffect {
    data class NavigateToDetail(val match: Match) : MatchesSideEffect
}
