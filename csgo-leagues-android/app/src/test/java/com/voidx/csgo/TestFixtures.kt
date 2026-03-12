package com.voidx.csgo

import com.voidx.csgo.domain.entity.League
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.MatchStatus
import com.voidx.csgo.domain.entity.PaginatedMatches
import com.voidx.csgo.domain.entity.Player
import com.voidx.csgo.domain.entity.Team
import java.time.LocalDateTime

fun league(
    id: Int = 1,
    name: String = "ESL Pro League",
    imageUrl: String? = "https://cdn.pandascore.co/images/league/image/$id/logo.png",
): League = League(
    id = id,
    name = name,
    imageUrl = imageUrl,
)

fun team(
    id: Int = 1,
    name: String = "Team $id",
    imageUrl: String? = "https://cdn.pandascore.co/images/team/image/$id/logo.png",
): Team = Team(
    id = id,
    name = name,
    imageUrl = imageUrl,
)

fun player(
    id: Int = 1,
    nickname: String? = "Player$id",
    fullName: String? = "Player $id",
    imageUrl: String? = "https://cdn.pandascore.co/images/player/image/$id/avatar.png",
): Player = Player(
    id = id,
    nickname = nickname,
    fullName = fullName,
    imageUrl = imageUrl,
)

fun match(
    id: Int = 1,
    status: MatchStatus = MatchStatus.Scheduled,
    beginAt: LocalDateTime? = LocalDateTime.of(2026, 3, 11, 12, 0),
    teams: List<Team> = listOf(team(1, "Alpha"), team(2, "Beta")),
    league: League = league(),
    serieName: String = "Group Stage",
): Match = Match(
    id = id,
    name = "Match $id",
    status = status,
    beginAt = beginAt,
    teams = teams,
    league = league,
    serieName = serieName,
)

fun paginatedMatches(
    matches: List<Match>,
    hasNextPage: Boolean,
): PaginatedMatches = PaginatedMatches(
    matches = matches,
    hasNextPage = hasNextPage,
)
