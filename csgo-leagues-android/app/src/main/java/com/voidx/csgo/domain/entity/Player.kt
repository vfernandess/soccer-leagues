package com.voidx.csgo.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val nickname: String? = null,
    val fullName: String? = null,
    val imageUrl: String? = null,
)
