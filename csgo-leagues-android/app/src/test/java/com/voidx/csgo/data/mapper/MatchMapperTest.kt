package com.voidx.csgo.data.mapper

import com.voidx.csgo.data.dto.LeagueDTO
import com.voidx.csgo.data.dto.MatchDTO
import com.voidx.csgo.data.dto.OpponentContainerDTO
import com.voidx.csgo.data.dto.SerieDTO
import com.voidx.csgo.data.dto.TeamDTO
import com.voidx.csgo.domain.entity.MatchStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class MatchMapperTest {

    @Test
    fun `maps dto into domain model with thumbnail urls`() {
        val dto = MatchDTO(
            id = 42,
            name = "NaVi vs Spirit",
            status = "running",
            beginAt = "2026-03-11T15:30:00Z",
            opponents = listOf(
                OpponentContainerDTO(
                    TeamDTO(
                        id = 10,
                        name = "NaVi",
                        imageUrl = "https://cdn.pandascore.co/images/team/image/10/logo.png",
                    ),
                ),
                OpponentContainerDTO(
                    TeamDTO(
                        id = 11,
                        name = "Spirit",
                        imageUrl = "https://cdn.pandascore.co/images/team/image/11/logo.png",
                    ),
                ),
            ),
            league = LeagueDTO(
                id = 3,
                name = "ESL Pro League",
                imageUrl = "https://cdn.pandascore.co/images/league/image/3/logo.png",
            ),
            serie = SerieDTO(
                id = 8,
                fullName = "Season 21",
            ),
        )

        val result = MatchMapper.toDomain(dto)

        assertEquals(42, result.id)
        assertEquals(MatchStatus.InProgress, result.status)
        assertNotNull(result.beginAt)
        assertEquals("https://cdn.pandascore.co/images/team/image/10/thumb_logo.png", result.teams[0].imageUrl)
        assertEquals("https://cdn.pandascore.co/images/league/image/3/thumb_logo.png", result.league.imageUrl)
        assertEquals("Season 21", result.serieName)
    }

    @Test
    fun `maps unknown statuses to scheduled`() {
        val dto = MatchDTO(
            id = 7,
            status = "not_started",
            league = LeagueDTO(id = 1, name = "League"),
            serie = SerieDTO(id = 1),
        )

        val result = MatchMapper.toDomain(dto)

        assertEquals(MatchStatus.Scheduled, result.status)
    }
}
