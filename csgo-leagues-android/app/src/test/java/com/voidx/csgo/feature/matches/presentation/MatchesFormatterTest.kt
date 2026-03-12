package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.league
import com.voidx.csgo.match
import com.voidx.csgo.team
import com.voidx.csgo.domain.entity.MatchStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MatchesFormatterTest {

    @Test
    fun `returns live badge for in progress matches`() {
        val result = MatchesFormatter.statusText(
            match = match(status = MatchStatus.InProgress),
            liveNowLabel = "AGORA",
            timeTbdLabel = "A definir",
            todayLabel = { time -> "Hoje, $time" },
        )

        assertEquals("AGORA", result)
    }

    @Test
    fun `formats todays matches in portuguese`() {
        val now = LocalDateTime.of(2026, 3, 11, 10, 0)
        val result = MatchesFormatter.statusText(
            match = match(beginAt = LocalDateTime.of(2026, 3, 11, 18, 30)),
            liveNowLabel = "AGORA",
            timeTbdLabel = "A definir",
            todayLabel = { time -> "Hoje, $time" },
            now = now,
        )

        assertEquals("Hoje, 18:30", result)
    }

    @Test
    fun `formats same week matches with weekday`() {
        val now = LocalDateTime.of(2026, 3, 11, 10, 0)
        val result = MatchesFormatter.statusText(
            match = match(beginAt = LocalDateTime.of(2026, 3, 13, 18, 30)),
            liveNowLabel = "AGORA",
            timeTbdLabel = "A definir",
            todayLabel = { time -> "Hoje, $time" },
            now = now,
        )

        assertEquals("Sex, 18:30", result)
    }

    @Test
    fun `formats older matches with day and month`() {
        val now = LocalDateTime.of(2026, 3, 11, 10, 0)
        val result = MatchesFormatter.statusText(
            match = match(beginAt = LocalDateTime.of(2026, 3, 20, 18, 30)),
            liveNowLabel = "AGORA",
            timeTbdLabel = "A definir",
            todayLabel = { time -> "Hoje, $time" },
            now = now,
        )

        assertEquals("20.03 18:30", result)
    }

    @Test
    fun `builds league label and team fallbacks`() {
        val match = match(
            teams = listOf(team(id = 1, name = ""), team(id = 2, name = "Spirit")),
            league = league(name = "ESL"),
            serieName = "Season 21",
        )

        assertEquals("ESL Season 21", MatchesFormatter.leagueLabel(match))
        assertNull(MatchesFormatter.teamAt(match, 0).name)
        assertNull(MatchesFormatter.teamAt(match, 8).name)
    }
}
