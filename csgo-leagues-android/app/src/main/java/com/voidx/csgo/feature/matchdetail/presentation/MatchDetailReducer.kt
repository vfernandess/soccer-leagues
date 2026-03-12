package com.voidx.csgo.feature.matchdetail.presentation

import com.voidx.csgo.core.arch.SimpleReducer
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailState

class MatchDetailReducer :
    SimpleReducer<MatchDetailState, MatchDetailEffect>(MatchDetailState()) {

    override fun reduce(
        effect: MatchDetailEffect,
        currentState: MatchDetailState,
    ): MatchDetailState = when (effect) {
        is MatchDetailEffect.Init -> currentState.copy(match = effect.match)
        MatchDetailEffect.Loading -> currentState.copy(
            isLoading = true,
            team1HasError = false,
            team2HasError = false,
        )
        is MatchDetailEffect.Team1PlayersLoaded -> currentState.copy(
            team1Players = effect.players,
            team1HasError = false,
        )
        is MatchDetailEffect.Team2PlayersLoaded -> currentState.copy(
            team2Players = effect.players,
            team2HasError = false,
        )
        MatchDetailEffect.Team1Error -> currentState.copy(team1HasError = true)
        MatchDetailEffect.Team2Error -> currentState.copy(team2HasError = true)
        MatchDetailEffect.LoadingCompleted -> currentState.copy(isLoading = false)
    }
}
