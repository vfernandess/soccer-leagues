package com.voidx.csgo.data.mapper

import com.voidx.csgo.data.dto.PlayerDTO
import com.voidx.csgo.domain.entity.Player

object PlayerMapper {
    fun toDomain(dto: PlayerDTO): Player =
        Player(
            id = dto.id,
            nickname = dto.name?.takeIf(String::isNotBlank),
            fullName = buildList {
                dto.firstName?.takeIf(String::isNotBlank)?.let(::add)
                dto.lastName?.takeIf(String::isNotBlank)?.let(::add)
            }.joinToString(" ").ifBlank { null },
            imageUrl = dto.imageUrl.toPandaScoreThumbnailUrl(),
        )
}
