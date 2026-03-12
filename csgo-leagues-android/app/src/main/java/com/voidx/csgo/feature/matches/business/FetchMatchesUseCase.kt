package com.voidx.csgo.feature.matches.business

import com.voidx.csgo.data.repository.MatchRepository
import com.voidx.csgo.domain.entity.PaginatedMatches
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchMatchesUseCase(
    private val matchRepository: MatchRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(page: Int, perPage: Int): PaginatedMatches =
        withContext(ioDispatcher) {
            matchRepository.fetchMatches(page = page, perPage = perPage)
        }
}
