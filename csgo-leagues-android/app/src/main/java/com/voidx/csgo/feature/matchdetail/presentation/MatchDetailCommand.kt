package com.voidx.csgo.feature.matchdetail.presentation

import com.voidx.csgo.domain.entity.Match

sealed interface MatchDetailCommand {
    data class LoadPlayers(val match: Match) : MatchDetailCommand
    data object Retry : MatchDetailCommand
}
