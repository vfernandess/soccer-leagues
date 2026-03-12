package com.voidx.csgo.data.repository

import com.voidx.csgo.domain.entity.Player

interface PlayerRepository {
    suspend fun fetchPlayers(teamId: Int): List<Player>
}
