package com.voidx.csgo.feature.matches.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voidx.csgo.R
import com.voidx.csgo.core.arch.compose.ext.OnApproachingListEnd
import com.voidx.csgo.core.arch.compose.ext.OnEffect
import com.voidx.csgo.domain.entity.Match
import com.voidx.csgo.core.theme.AppBackground
import com.voidx.csgo.core.theme.AppTextStyle
import com.voidx.csgo.core.theme.DS
import com.voidx.csgo.feature.matches.business.MatchesInteractor
import com.voidx.csgo.feature.matches.presentation.MatchesCommand
import com.voidx.csgo.feature.matches.presentation.MatchesContentPhase
import com.voidx.csgo.feature.matches.presentation.MatchesError
import com.voidx.csgo.feature.matches.presentation.MatchesSideEffect
import com.voidx.csgo.feature.matches.presentation.MatchesViewModel
import com.voidx.csgo.feature.matches.ui.components.EmptyMatchesContent
import com.voidx.csgo.feature.matches.ui.components.ErrorMatchesContent
import com.voidx.csgo.feature.matches.ui.components.MatchCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
    onNavigateToDetail: (Match) -> Unit,
    viewModel: MatchesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    OnEffect(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is MatchesSideEffect.NavigateToDetail -> onNavigateToDetail(sideEffect.match)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.dispatch(MatchesCommand.Load)
    }

    listState.OnApproachingListEnd(
        enable = state.hasMorePages && !state.isLoading && !state.isRefreshing && !state.isLoadingMore,
        endOffset = MatchesInteractor.PAGINATION_THRESHOLD,
    ) {
        viewModel.dispatch(MatchesCommand.LoadNextPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Text(
            text = stringResource(R.string.matches_title),
            style = AppTextStyle.ScreenTitle,
            modifier = Modifier.padding(start = DS.Size.s6, top = DS.Size.s6)
        )

        when (val phase = state.contentPhase) {
            MatchesContentPhase.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            MatchesContentPhase.Empty -> EmptyMatchesContent(
                modifier = Modifier.fillMaxSize()
            )

            is MatchesContentPhase.Error -> ErrorMatchesContent(
                message =  stringResource(R.string.matches_error_generic),
                onRetry = { viewModel.dispatch(MatchesCommand.Retry) },
                modifier = Modifier.fillMaxSize()
            )

            MatchesContentPhase.Loaded -> {
                PullToRefreshBox(
                    isRefreshing = state.isRefreshing,
                    onRefresh = { viewModel.dispatch(MatchesCommand.Refresh) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(
                            horizontal = DS.Size.s6,
                            vertical = DS.Size.s4
                        ),
                        verticalArrangement = Arrangement.spacedBy(DS.Size.s4)
                    ) {

                        items(
                            items = state.matches,
                            key = { it.id }
                        ) { match ->
                            MatchCard(
                                match = match,
                                onClick = {
                                    viewModel.dispatch(MatchesCommand.MatchTapped(match))
                                }
                            )
                        }

                        if (state.isLoadingMore) {
                            item(key = "pagination-loader") {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(DS.Size.s4),
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
