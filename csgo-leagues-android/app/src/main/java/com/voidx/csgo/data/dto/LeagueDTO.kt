package com.voidx.csgo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeagueDTO(
    val id: Int,
    val name: String,
    @SerialName("image_url") val imageUrl: String? = null,
)
