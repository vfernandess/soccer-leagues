package com.voidx.csgo.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class League(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
)
