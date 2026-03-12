package com.voidx.csgo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDTO(
    val id: Int,
    val name: String = "",
    val status: String,
    @SerialName("begin_at") val beginAt: String? = null,
    val opponents: List<OpponentContainerDTO> = emptyList(),
    val league: LeagueDTO,
    val serie: SerieDTO,
)
