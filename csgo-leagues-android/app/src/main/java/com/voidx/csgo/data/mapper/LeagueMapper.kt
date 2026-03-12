package com.voidx.csgo.data.mapper

import com.voidx.csgo.data.dto.LeagueDTO
import com.voidx.csgo.domain.entity.League

object LeagueMapper {
    fun toDomain(dto: LeagueDTO): League =
        League(
            id = dto.id,
            name = dto.name,
            imageUrl = dto.imageUrl.toPandaScoreThumbnailUrl(),
        )
}
