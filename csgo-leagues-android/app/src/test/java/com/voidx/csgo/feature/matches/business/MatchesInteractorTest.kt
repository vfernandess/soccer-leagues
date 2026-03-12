package com.voidx.csgo.feature.matches.business

import app.cash.turbine.test
import com.voidx.csgo.data.repository.MatchRepository
import com.voidx.csgo.match
import com.voidx.csgo.paginatedMatches
import com.voidx.csgo.domain.entity.PaginatedMatches
import com.voidx.csgo.feature.matches.presentation.MatchesCommand
import com.voidx.csgo.feature.matches.presentation.MatchesEffect
import com.voidx.csgo.feature.matches.presentation.MatchesSideEffect
import com.voidx.csgo.feature.matches.presentation.MatchesState
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatchesInteractorTest {

    @Test
    fun `load emits loading then first page`() = runTest {
        val repository = FakeMatchRepository().apply {
            results[1] = paginatedMatches(
                matches = listOf(match(id = 1), match(id = 2)),
                hasNextPage = true,
            )
        }
        val interactor = MatchesInteractor(
            FetchMatchesUseCase(
                matchRepository = repository,
                ioDispatcher = StandardTestDispatcher(testScheduler),
            ),
        )

        interactor.invoke(MatchesState(), MatchesCommand.Load).test {
            assertEquals(MatchesEffect.Loading, awaitItem())
            assertEquals(
                MatchesEffect.MatchesLoaded(
                    matches = listOf(match(id = 1), match(id = 2)),
                    hasNext = true,
                ),
                awaitItem(),
            )
            awaitComplete()
        }

        assertEquals(listOf(1 to 50), repository.calls)
    }

    @Test
    fun `load next page fetches the next page`() = runTest {
        val repository = FakeMatchRepository().apply {
            results[2] = paginatedMatches(
                matches = listOf(match(id = 51)),
                hasNextPage = false,
            )
        }
        val interactor = MatchesInteractor(
            FetchMatchesUseCase(
                matchRepository = repository,
                ioDispatcher = StandardTestDispatcher(testScheduler),
            ),
        )
        val state = MatchesState(
            matches = (1..20).map { match(id = it) },
            currentPage = 1,
            hasMorePages = true,
        )

        interactor.invoke(state, MatchesCommand.LoadNextPage).test {
            assertEquals(MatchesEffect.LoadingMore, awaitItem())
            assertEquals(
                MatchesEffect.PageLoaded(matches = listOf(match(id = 51)), hasNext = false),
                awaitItem(),
            )
            awaitComplete()
        }

        assertEquals(listOf(2 to 50), repository.calls)
    }

    @Test
    fun `match tapped sends navigation side effect`() = runTest {
        val interactor = MatchesInteractor(
            FetchMatchesUseCase(
                matchRepository = FakeMatchRepository(),
                ioDispatcher = StandardTestDispatcher(testScheduler),
            ),
        )
        val targetMatch = match(id = 9)

        interactor.invoke(MatchesState(), MatchesCommand.MatchTapped(targetMatch)).test {
            assertEquals(MatchesEffect.NavigationRequested, awaitItem())
            awaitComplete()
        }

        interactor.sideEffect.test {
            assertEquals(
                MatchesSideEffect.NavigateToDetail(targetMatch),
                awaitItem(),
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `pagination errors do not clear existing content`() = runTest {
        val repository = FakeMatchRepository().apply {
            failures[2] = IllegalStateException("boom")
        }
        val interactor = MatchesInteractor(
            FetchMatchesUseCase(
                matchRepository = repository,
                ioDispatcher = StandardTestDispatcher(testScheduler),
            ),
        )
        val state = MatchesState(
            matches = listOf(match(id = 1)),
            currentPage = 1,
            hasMorePages = true,
        )

        interactor.invoke(state, MatchesCommand.LoadNextPage).test {
            assertEquals(MatchesEffect.LoadingMore, awaitItem())
            assertEquals(MatchesEffect.PaginationError, awaitItem())
            awaitComplete()
        }
    }

    private class FakeMatchRepository : MatchRepository {
        val calls = mutableListOf<Pair<Int, Int>>()
        val results = mutableMapOf<Int, PaginatedMatches>()
        val failures = mutableMapOf<Int, Throwable>()

        override suspend fun fetchMatches(page: Int, perPage: Int): PaginatedMatches {
            calls += page to perPage
            failures[page]?.let { throw it }
            return checkNotNull(results[page]) { "Missing result for page $page" }
        }
    }
}
