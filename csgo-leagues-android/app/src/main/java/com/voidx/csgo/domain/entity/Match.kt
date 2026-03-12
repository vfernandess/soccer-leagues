package com.voidx.csgo.domain.entity

import java.time.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Match(
    val id: Int,
    val name: String,
    val status: MatchStatus,
    @Contextual val beginAt: LocalDateTime? = null,
    val teams: List<Team> = emptyList(),
    val league: League,
    val serieName: String,
)
