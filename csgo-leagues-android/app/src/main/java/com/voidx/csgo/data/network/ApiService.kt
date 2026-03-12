package com.voidx.csgo.data.network

import com.voidx.csgo.data.dto.MatchDTO
import com.voidx.csgo.data.dto.PlayerDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(
    private val client: HttpClient,
) {
    suspend fun fetchMatches(page: Int, perPage: Int): Pair<List<MatchDTO>, Boolean> {
        val response = client.get("/csgo/matches") {
            parameter("page[number]", page)
            parameter("page[size]", perPage)
            parameter("sort", "-status,begin_at")
            parameter("filter[status]", "finished,not_started,running")
        }

        return response.body<List<MatchDTO>>() to response.hasNextPage()
    }

    suspend fun fetchPlayers(teamId: Int): List<PlayerDTO> =
        client.get("/csgo/players") {
            parameter("filter[team_id]", teamId)
        }.body()

    private fun io.ktor.client.statement.HttpResponse.hasNextPage(): Boolean =
        headers["Link"]?.contains("rel=\"next\"") == true
}
