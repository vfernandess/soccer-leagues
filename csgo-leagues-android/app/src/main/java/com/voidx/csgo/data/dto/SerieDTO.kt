package com.voidx.csgo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerieDTO(
    val id: Int,
    @SerialName("full_name") val fullName: String? = null,
)
