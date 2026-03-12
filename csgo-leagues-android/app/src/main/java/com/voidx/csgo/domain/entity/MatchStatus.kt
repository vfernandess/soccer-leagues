package com.voidx.csgo.domain.entity

import kotlinx.serialization.Serializable

@Serializable
enum class MatchStatus {
    InProgress,
    Scheduled,
    Ended,
}
