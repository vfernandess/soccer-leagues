package com.voidx.csgo.feature.matchdetail.business

import com.voidx.csgo.player
import com.voidx.csgo.domain.entity.Player
import com.voidx.csgo.data.repository.PlayerRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FetchPlayersUseCaseTest {

    @Test
    fun `delegates team id to repository`() = runTest {
        val repository = RecordingPlayerRepository(
            result = listOf(player(id = 7)),
        )
        val useCase = FetchPlayersUseCase(
            playerRepository = repository,
            ioDispatcher = StandardTestDispatcher(testScheduler),
        )

        val result = useCase(teamId = 22)

        assertEquals(listOf(22), repository.calls)
        assertEquals(7, result.single().id)
    }

    private class RecordingPlayerRepository(
        private val result: List<Player>,
    ) : PlayerRepository {
        val calls = mutableListOf<Int>()

        override suspend fun fetchPlayers(teamId: Int): List<Player> {
            calls += teamId
            return result
        }
    }
}
