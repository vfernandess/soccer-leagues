package com.voidx.csgo.data.repository

import com.voidx.csgo.domain.entity.PaginatedMatches

interface MatchRepository {
    suspend fun fetchMatches(page: Int, perPage: Int): PaginatedMatches
}
