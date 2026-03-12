package com.voidx.csgo.feature.matches.presentation

import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.MatchStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

internal object MatchesFormatter {
    private val ptBr = Locale.forLanguageTag("pt-BR")
    private val sameWeekFormatter = DateTimeFormatter.ofPattern("EEE, HH:mm", ptBr)
    private val fallbackFormatter = DateTimeFormatter.ofPattern("dd.MM HH:mm", ptBr)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", ptBr)

    fun statusText(
        match: Match,
        liveNowLabel: String,
        timeTbdLabel: String,
        todayLabel: (String) -> String,
        now: LocalDateTime = LocalDateTime.now(),
    ): String = when {
        match.status == MatchStatus.InProgress -> liveNowLabel
        match.beginAt == null -> timeTbdLabel
        match.beginAt.toLocalDate() == now.toLocalDate() ->
            todayLabel(match.beginAt.format(timeFormatter))
        isWithinCurrentWeek(match.beginAt.toLocalDate(), now.toLocalDate()) ->
            capitalizeWeekday(match.beginAt.format(sameWeekFormatter))
        else -> match.beginAt.format(fallbackFormatter)
    }

    fun leagueLabel(match: Match): String = listOfNotNull(
        match.league.name.takeIf { it.isNotBlank() },
        match.serieName.takeIf { it.isNotBlank() }
    ).joinToString(" ")

    fun teamAt(match: Match, index: Int): TeamDisplay = match.teams.getOrNull(index)?.let { team ->
        TeamDisplay(
            name = team.name.takeIf { it.isNotBlank() },
            imageUrl = team.imageUrl
        )
    } ?: TeamDisplay()

    private fun isWithinCurrentWeek(matchDate: LocalDate, nowDate: LocalDate): Boolean {
        val weekFields = WeekFields.of(ptBr)
        return matchDate.get(weekFields.weekOfWeekBasedYear()) ==
            nowDate.get(weekFields.weekOfWeekBasedYear()) &&
            matchDate.year == nowDate.year &&
            matchDate != nowDate
    }

    private fun capitalizeWeekday(value: String): String {
        if (value.isBlank()) return value
        val normalized = value.replace(".", "")
        return normalized.replaceFirstChar { char ->
            if (char.isLowerCase()) {
                char.titlecase(ptBr)
            } else {
                char.toString()
            }
        }
    }
}

internal data class TeamDisplay(
    val name: String? = null,
    val imageUrl: String? = null
)
