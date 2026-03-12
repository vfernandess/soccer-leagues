package com.voidx.csgo.feature.matchdetail.business

import com.voidx.csgo.domain.entity.Player
import com.voidx.csgo.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchPlayersUseCase(
    private val playerRepository: PlayerRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(teamId: Int): List<Player> = withContext(ioDispatcher) {
        playerRepository.fetchPlayers(teamId)
    }
}