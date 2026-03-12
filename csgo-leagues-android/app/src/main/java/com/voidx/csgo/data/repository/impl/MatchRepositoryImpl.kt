package com.voidx.csgo.data.repository.impl

import com.voidx.csgo.data.mapper.MatchMapper
import com.voidx.csgo.data.network.ApiService
import com.voidx.csgo.data.repository.MatchRepository
import com.voidx.csgo.domain.entity.PaginatedMatches

class MatchRepositoryImpl(
    private val apiService: ApiService,
) : MatchRepository {
    override suspend fun fetchMatches(page: Int, perPage: Int): PaginatedMatches {
        val (matches, hasNextPage) = apiService.fetchMatches(page = page, perPage = perPage)
        return PaginatedMatches(
            matches = matches.map(MatchMapper::toDomain),
            hasNextPage = hasNextPage,
        )
    }
}
