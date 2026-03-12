package com.voidx.csgo.data.mapper

import com.voidx.csgo.data.dto.TeamDTO
import com.voidx.csgo.domain.entity.Team

object TeamMapper {
    fun toDomain(dto: TeamDTO): Team =
        Team(
            id = dto.id,
            name = dto.name,
            imageUrl = dto.imageUrl.toPandaScoreThumbnailUrl(),
        )
}
