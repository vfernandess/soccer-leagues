package com.voidx.csgo.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
)
