package com.voidx.csgo.feature.matchdetail.presentation

import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.Player

data class MatchDetailState(
    val match: Match? = null,
    val team1Players: List<Player> = emptyList(),
    val team2Players: List<Player> = emptyList(),
    val team1HasError: Boolean = false,
    val team2HasError: Boolean = false,
    val isLoading: Boolean = false,
)