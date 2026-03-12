package com.voidx.csgo.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class OpponentContainerDTO(
    val opponent: TeamDTO,
)
