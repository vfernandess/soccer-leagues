package com.voidx.csgo.feature.matches.business

import com.voidx.csgo.match
import com.voidx.csgo.paginatedMatches
import com.voidx.csgo.domain.entity.PaginatedMatches
import com.voidx.csgo.data.repository.MatchRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FetchMatchesUseCaseTest {

    @Test
    fun `delegates paging params to repository`() = runTest {
        val repository = RecordingMatchRepository(
            result = paginatedMatches(matches = listOf(match(id = 1)), hasNextPage = true),
        )
        val useCase = FetchMatchesUseCase(
            matchRepository = repository,
            ioDispatcher = StandardTestDispatcher(testScheduler),
        )

        val result = useCase(page = 3, perPage = 50)

        assertEquals(listOf(3 to 50), repository.calls)
        assertEquals(true, result.hasNextPage)
        assertEquals(1, result.matches.single().id)
    }

    private class RecordingMatchRepository(
        private val result: PaginatedMatches,
    ) : MatchRepository {
        val calls = mutableListOf<Pair<Int, Int>>()

        override suspend fun fetchMatches(page: Int, perPage: Int): PaginatedMatches {
            calls += page to perPage
            return result
        }
    }
}
