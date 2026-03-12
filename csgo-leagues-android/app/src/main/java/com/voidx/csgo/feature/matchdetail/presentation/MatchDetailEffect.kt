package com.voidx.csgo.feature.matchdetail.presentation

import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.Player

sealed interface MatchDetailEffect {
    data class Init(val match: Match) : MatchDetailEffect
    data object Loading : MatchDetailEffect
    data class Team1PlayersLoaded(val players: List<Player>) : MatchDetailEffect
    data class Team2PlayersLoaded(val players: List<Player>) : MatchDetailEffect
    data object Team1Error : MatchDetailEffect
    data object Team2Error : MatchDetailEffect
    data object LoadingCompleted : MatchDetailEffect
}
