package com.voidx.csgo.feature.matchdetail.business

import app.cash.turbine.test
import com.voidx.csgo.match
import com.voidx.csgo.player
import com.voidx.csgo.team
import com.voidx.csgo.data.repository.PlayerRepository
import com.voidx.csgo.domain.entity.Player
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailCommand
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailEffect
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailState
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatchDetailInteractorTest {

    @Test
    fun `load players supports partial failure`() = runTest {
        val repository = FakePlayerRepository().apply {
            results[1] = listOf(player(id = 101))
            failures[2] = IllegalStateException("boom")
        }
        val interactor = MatchDetailInteractor(
            FetchPlayersUseCase(
                playerRepository = repository,
                ioDispatcher = StandardTestDispatcher(testScheduler),
            ),
        )
        val selectedMatch = match(
            teams = listOf(team(id = 1, name = "Alpha"), team(id = 2, name = "Beta")),
        )

        interactor.invoke(MatchDetailState(), MatchDetailCommand.LoadPlayers(selectedMatch)).test {
            assertEquals(MatchDetailEffect.Init(selectedMatch), awaitItem())
            assertEquals(MatchDetailEffect.Loading, awaitItem())
            assertEquals(
                MatchDetailEffect.Team1PlayersLoaded(listOf(player(id = 101))),
                awaitItem(),
            )
            assertEquals(
                MatchDetailEffect.Team2Error,
                awaitItem(),
            )
            assertEquals(MatchDetailEffect.LoadingCompleted, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `retry without a selected match emits nothing`() = runTest {
        val interactor = MatchDetailInteractor(
            FetchPlayersUseCase(
                playerRepository = FakePlayerRepository(),
                ioDispatcher = StandardTestDispatcher(testScheduler),
            ),
        )

        interactor.invoke(MatchDetailState(), MatchDetailCommand.Retry).test {
            awaitComplete()
        }
    }

    private class FakePlayerRepository : PlayerRepository {
        val results = mutableMapOf<Int, List<Player>>()
        val failures = mutableMapOf<Int, Throwable>()

        override suspend fun fetchPlayers(teamId: Int): List<Player> {
            failures[teamId]?.let { throw it }
            return results[teamId].orEmpty()
        }
    }
}
