package com.voidx.csgo.feature.matchdetail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.voidx.csgo.R
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.core.theme.AppBackground
import com.voidx.csgo.core.theme.AppTextStyle
import com.voidx.csgo.core.theme.DS
import com.voidx.csgo.core.theme.Placeholder
import com.voidx.csgo.domain.entity.Player
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailCommand
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailViewModel
import com.voidx.csgo.feature.matchdetail.ui.components.PlayerRow
import com.voidx.csgo.feature.matchdetail.ui.components.PlayerRowAlignment
import com.voidx.csgo.feature.matches.presentation.MatchesFormatter
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailScreen(
    match: Match,
    onBack: () -> Unit,
    viewModel: MatchDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentMatch = state.match ?: match

    LaunchedEffect(match.id) {
        viewModel.dispatch(MatchDetailCommand.LoadPlayers(match))
    }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = MatchesFormatter.leagueLabel(currentMatch),
                        style = AppTextStyle.NavTitle,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.match_detail_back_content_description),
                            modifier = Modifier.size(DS.Icon.i6),
                            tint = Color.White,
                        )
                    }
                },
            )
        },
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(AppBackground),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = DS.Size.s2)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TeamsSection(match = currentMatch)
                Spacer(modifier = Modifier.height(DS.Size.s5))
                val liveNowLabel = stringResource(R.string.matches_live_now)
                val timeTbdLabel = stringResource(R.string.matches_time_tbd)
                val todayPrefix = stringResource(R.string.matches_today_prefix)
                val statusText = remember(currentMatch, liveNowLabel, timeTbdLabel, todayPrefix) {
                    MatchesFormatter.statusText(
                        match = currentMatch,
                        liveNowLabel = liveNowLabel,
                        timeTbdLabel = timeTbdLabel,
                        todayLabel = { time -> "$todayPrefix, $time" },
                    )
                }
                Text(
                    text = statusText,
                    style = AppTextStyle.MatchTime,
                )
                Spacer(modifier = Modifier.height(DS.Size.s5))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(DS.Component.playerColumnGap),
                ) {
                    TeamRosterColumn(
                        players = state.team1Players,
                        hasError = state.team1HasError,
                        alignment = PlayerRowAlignment.LEFT,
                        modifier = Modifier.weight(1f),
                    )
                    TeamRosterColumn(
                        players = state.team2Players,
                        hasError = state.team2HasError,
                        alignment = PlayerRowAlignment.RIGHT,
                        modifier = Modifier.weight(1f),
                    )
                }

                if (state.team1HasError && state.team2HasError) {
                    Spacer(modifier = Modifier.height(DS.Size.s4))
                    TextButton(
                        onClick = { viewModel.dispatch(MatchDetailCommand.Retry) },
                        modifier = Modifier.padding(bottom = DS.Size.s6),
                    ) {
                        Text(
                            text = stringResource(R.string.match_detail_retry),
                            style = AppTextStyle.FeedbackAction,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TeamsSection(match: Match) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TeamSummary(
            name = match.teams.getOrNull(0)?.name.orEmpty().ifBlank {
                stringResource(R.string.match_detail_tbd)
            },
            imageUrl = match.teams.getOrNull(0)?.imageUrl,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = stringResource(R.string.match_detail_vs),
            style = AppTextStyle.Vs,
        )
        TeamSummary(
            name = match.teams.getOrNull(1)?.name.orEmpty().ifBlank {
                stringResource(R.string.match_detail_tbd)
            },
            imageUrl = match.teams.getOrNull(1)?.imageUrl,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun TeamSummary(
    name: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (imageUrl.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .size(DS.Icon.i15)
                    .clip(CircleShape)
                    .background(Placeholder),
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(
                    R.string.match_detail_team_logo_content_description,
                    name,
                ),
                modifier = Modifier
                    .size(DS.Icon.i15)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Placeholder),
                error = ColorPainter(Placeholder),
            )
        }
        Spacer(modifier = Modifier.height(DS.Component.teamNameSpacing))
        Text(
            text = name,
            style = AppTextStyle.TeamName,
        )
    }
}

@Composable
private fun TeamRosterColumn(
    players: List<Player>,
    hasError: Boolean,
    alignment: PlayerRowAlignment,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when {
            hasError -> {
                Text(
                    text = stringResource(R.string.match_detail_generic_error),
                    style = AppTextStyle.FeedbackMessage,
                )
            }
            players.isEmpty() -> {
                Text(
                    text = stringResource(R.string.match_detail_tbd),
                    style = AppTextStyle.FeedbackMessage,
                )
            }
            else -> {
                players.forEachIndexed { index, player ->
                    PlayerRow(
                        player = player,
                        alignment = alignment,
                    )
                    if (index != players.lastIndex) {
                        Spacer(modifier = Modifier.height(DS.Size.s3))
                    }
                }
            }
        }
    }
}
