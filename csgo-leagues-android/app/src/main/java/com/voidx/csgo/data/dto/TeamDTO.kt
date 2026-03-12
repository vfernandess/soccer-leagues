package com.voidx.csgo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(
    val id: Int,
    val name: String = "",
    @SerialName("image_url") val imageUrl: String? = null,
)
