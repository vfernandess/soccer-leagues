package com.voidx.csgo.domain.entity

data class PaginatedMatches(
    val matches: List<Match>,
    val hasNextPage: Boolean,
)
