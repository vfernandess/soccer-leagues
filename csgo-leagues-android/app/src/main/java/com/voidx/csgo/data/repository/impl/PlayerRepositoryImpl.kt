package com.voidx.csgo.data.repository.impl

import com.voidx.csgo.data.mapper.PlayerMapper
import com.voidx.csgo.data.network.ApiService
import com.voidx.csgo.data.repository.PlayerRepository
import com.voidx.csgo.domain.entity.Player

class PlayerRepositoryImpl(
    private val apiService: ApiService,
) : PlayerRepository {
    override suspend fun fetchPlayers(teamId: Int): List<Player> =
        apiService.fetchPlayers(teamId).map(PlayerMapper::toDomain)
}
