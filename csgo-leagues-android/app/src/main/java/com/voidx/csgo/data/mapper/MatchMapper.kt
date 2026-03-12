package com.voidx.csgo.data.mapper

import com.voidx.csgo.data.dto.MatchDTO
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.MatchStatus
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object MatchMapper {
    fun toDomain(dto: MatchDTO): Match =
        Match(
            id = dto.id,
            name = dto.name,
            status = dto.status.toDomainStatus(),
            beginAt = dto.beginAt.toLocalDateTime(),
            teams = dto.opponents.map { TeamMapper.toDomain(it.opponent) },
            league = LeagueMapper.toDomain(dto.league),
            serieName = dto.serie.fullName.orEmpty(),
        )

    private fun String.toDomainStatus(): MatchStatus = when (this) {
        "running" -> MatchStatus.InProgress
        "finished" -> MatchStatus.Ended
        else -> MatchStatus.Scheduled
    }

    private fun String?.toLocalDateTime(): LocalDateTime? {
        val raw = this?.takeIf(String::isNotBlank) ?: return null
        return runCatching {
            Instant.parse(raw)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }.getOrNull()
    }
}
