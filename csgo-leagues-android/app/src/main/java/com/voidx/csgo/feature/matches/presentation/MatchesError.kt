package com.voidx.csgo.feature.matches.presentation

sealed interface MatchesError {
    data object Generic : MatchesError
}
