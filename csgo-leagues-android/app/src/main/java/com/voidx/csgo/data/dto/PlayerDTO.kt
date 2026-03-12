package com.voidx.csgo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val id: Int,
    val name: String? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
)
