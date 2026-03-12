package com.voidx.csgo.feature.matches.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.voidx.csgo.R
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.domain.entity.MatchStatus
import com.voidx.csgo.core.theme.AppShape
import com.voidx.csgo.core.theme.AppTextStyle
import com.voidx.csgo.core.theme.DS
import com.voidx.csgo.core.theme.LiveBadge
import com.voidx.csgo.core.theme.ScheduledBadge
import com.voidx.csgo.feature.matches.presentation.MatchesFormatter

@Composable
fun MatchStatusBadge(
    match: Match,
    modifier: Modifier = Modifier
) {
    val liveNowLabel = stringResource(R.string.matches_live_now)
    val timeTbdLabel = stringResource(R.string.matches_time_tbd)
    val todayPrefix = stringResource(R.string.matches_today_prefix)
    val badgeText = remember(match, liveNowLabel, timeTbdLabel, todayPrefix) {
        MatchesFormatter.statusText(
            match = match,
            liveNowLabel = liveNowLabel,
            timeTbdLabel = timeTbdLabel,
            todayLabel = { time -> "$todayPrefix, $time" },
        )
    }

    Box(
        modifier = modifier
            .background(
                color = if (match.status == MatchStatus.InProgress) LiveBadge else ScheduledBadge,
                shape = AppShape.Badge
            )
            .padding(DS.Size.s2)
    ) {
        Text(
            text = badgeText,
            style = AppTextStyle.Badge
        )
    }
}
