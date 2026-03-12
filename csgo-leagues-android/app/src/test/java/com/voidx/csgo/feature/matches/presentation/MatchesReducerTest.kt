package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.match
import com.voidx.csgo.domain.entity.MatchStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MatchesReducerTest {

    private val reducer = MatchesReducer()

    @Test
    fun `matches loaded sorts live first then upcoming then ended`() {
        val scheduled = match(
            id = 2,
            status = MatchStatus.Scheduled,
            beginAt = LocalDateTime.of(2026, 3, 11, 19, 0),
        )
        val live = match(
            id = 1,
            status = MatchStatus.InProgress,
            beginAt = LocalDateTime.of(2026, 3, 11, 20, 0),
        )
        val ended = match(
            id = 3,
            status = MatchStatus.Ended,
            beginAt = LocalDateTime.of(2026, 3, 11, 18, 0),
        )

        val result = reducer.reduce(
            MatchesEffect.MatchesLoaded(listOf(ended, scheduled, live), hasNext = true),
            MatchesState(isLoading = true),
        )

        assertEquals(listOf(1, 2, 3), result.matches.map { it.id })
        assertEquals(1, result.currentPage)
        assertEquals(true, result.hasMorePages)
        assertNull(result.error)
    }

    @Test
    fun `page loaded deduplicates by id and advances page`() {
        val existing = match(id = 1)
        val duplicate = match(id = 1, beginAt = LocalDateTime.of(2026, 3, 11, 14, 0))
        val next = match(id = 2)

        val result = reducer.reduce(
            MatchesEffect.PageLoaded(listOf(duplicate, next), hasNext = false),
            MatchesState(matches = listOf(existing), currentPage = 1, isLoadingMore = true, hasMorePages = true),
        )

        assertEquals(listOf(1, 2), result.matches.map { it.id })
        assertEquals(2, result.currentPage)
        assertEquals(false, result.hasMorePages)
    }

    @Test
    fun `error is hidden when content already exists`() {
        val result = reducer.reduce(
            MatchesEffect.Error(MatchesError.Generic),
            MatchesState(matches = listOf(match(id = 1)), isLoadingMore = true),
        )

        assertNull(result.error)
        assertEquals(false, result.isLoadingMore)
    }
}
