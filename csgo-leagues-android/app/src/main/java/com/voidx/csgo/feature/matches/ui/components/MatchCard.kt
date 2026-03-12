package com.voidx.csgo.feature.matches.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.voidx.csgo.R
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.core.theme.AppShape
import com.voidx.csgo.core.theme.AppTextStyle
import com.voidx.csgo.core.theme.CardBackground
import com.voidx.csgo.core.theme.DS
import com.voidx.csgo.core.theme.Placeholder
import com.voidx.csgo.feature.matches.presentation.MatchesFormatter
import com.voidx.csgo.feature.matches.presentation.TeamDisplay

@Composable
fun MatchCard(
    match: Match,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val team1 = MatchesFormatter.teamAt(match, 0)
    val team2 = MatchesFormatter.teamAt(match, 1)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppShape.Card)
            .background(CardBackground)
            .clickable(onClick = onClick)
    ) {
        MatchStatusBadge(
            match = match,
            modifier = Modifier.align(Alignment.TopEnd)
        )

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DS.Component.teamsContainerHeight)
                    .padding(DS.Size.s3),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamSlot(team = team1)
                Spacer(modifier = Modifier.size(DS.Size.s5))
                Text(text = stringResource(R.string.matches_versus), style = AppTextStyle.Vs)
                Spacer(modifier = Modifier.size(DS.Size.s5))
                TeamSlot(team = team2)
            }

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = DS.Size.s4,
                        top = DS.Size.s2,
                        end = DS.Size.s2,
                        bottom = DS.Size.s2
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = match.league.imageUrl,
                    contentDescription = match.league.name,
                    modifier = Modifier.size(DS.Icon.i4),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(DS.Size.s2))
                Text(
                    text = MatchesFormatter.leagueLabel(match),
                    style = AppTextStyle.League
                )
            }
        }
    }
}

@Composable
private fun TeamSlot(
    team: TeamDisplay,
    modifier: Modifier = Modifier
) {
    val teamName = team.name ?: stringResource(R.string.matches_team_tbd)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (team.imageUrl.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .size(DS.Icon.i15)
                    .clip(CircleShape)
                    .background(Placeholder)
            )
        } else {
            AsyncImage(
                model = team.imageUrl,
                contentDescription = teamName,
                modifier = Modifier
                    .size(DS.Icon.i15)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = ColorPainter(Placeholder),
                placeholder = ColorPainter(Placeholder)
            )
        }

        Spacer(modifier = Modifier.height(DS.Component.teamNameSpacing))
        Text(
            text = teamName,
            style = AppTextStyle.TeamName
        )
    }
}
