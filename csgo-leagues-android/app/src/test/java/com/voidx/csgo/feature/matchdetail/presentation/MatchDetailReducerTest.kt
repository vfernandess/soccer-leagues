package com.voidx.csgo.feature.matchdetail.presentation

import com.voidx.csgo.match
import com.voidx.csgo.player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatchDetailReducerTest {

    private val reducer = MatchDetailReducer()

    @Test
    fun `loading clears previous errors`() {
        val result = reducer.reduce(
            MatchDetailEffect.Loading,
            MatchDetailState(team1HasError = true, team2HasError = true),
        )

        assertEquals(true, result.isLoading)
        assertEquals(false, result.team1HasError)
        assertEquals(false, result.team2HasError)
    }

    @Test
    fun `team player loads update the corresponding side`() {
        val loadedLeft = reducer.reduce(
            MatchDetailEffect.Team1PlayersLoaded(listOf(player(id = 1))),
            MatchDetailState(team1HasError = true),
        )
        val loadedRight = reducer.reduce(
            MatchDetailEffect.Team2PlayersLoaded(listOf(player(id = 2))),
            loadedLeft,
        )

        assertEquals(listOf(1), loadedRight.team1Players.map { it.id })
        assertEquals(listOf(2), loadedRight.team2Players.map { it.id })
        assertEquals(false, loadedRight.team1HasError)
    }

    @Test
    fun `init and loading completed keep the selected match and end loading`() {
        val initialized = reducer.reduce(
            MatchDetailEffect.Init(match(id = 5)),
            MatchDetailState(),
        )
        val completed = reducer.reduce(
            MatchDetailEffect.LoadingCompleted,
            initialized.copy(isLoading = true),
        )

        assertEquals(5, completed.match?.id)
        assertEquals(false, completed.isLoading)
    }
}
