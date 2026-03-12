package com.voidx.csgo.feature.matches.business

import com.voidx.csgo.core.arch.SideEffectInteractor
import com.voidx.csgo.feature.matches.presentation.MatchesCommand
import com.voidx.csgo.feature.matches.presentation.MatchesEffect
import com.voidx.csgo.feature.matches.presentation.MatchesError
import com.voidx.csgo.feature.matches.presentation.MatchesSideEffect
import com.voidx.csgo.feature.matches.presentation.MatchesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MatchesInteractor(
    private val fetchMatchesUseCase: FetchMatchesUseCase
) : SideEffectInteractor<MatchesState, MatchesCommand, MatchesEffect, MatchesSideEffect>() {

    override suspend fun invoke(
        state: MatchesState,
        command: MatchesCommand
    ): Flow<MatchesEffect> = when (command) {
        MatchesCommand.Load -> flow {
            if (state.matches.isNotEmpty() || state.isLoading) {
                return@flow
            }

            emit(MatchesEffect.Loading)
            try {
                val result = fetchMatchesUseCase(page = PAGE_SIZE_INITIAL_PAGE, perPage = PAGE_SIZE)
                emit(MatchesEffect.MatchesLoaded(result.matches, result.hasNextPage))
            } catch (_: Exception) {
                emit(MatchesEffect.Error(MatchesError.Generic))
            }
        }

        MatchesCommand.Refresh -> flow {
            if (state.isRefreshing || state.isLoading) {
                return@flow
            }

            emit(MatchesEffect.Refreshing)
            try {
                val result = fetchMatchesUseCase(page = PAGE_SIZE_INITIAL_PAGE, perPage = PAGE_SIZE)
                emit(MatchesEffect.MatchesLoaded(result.matches, result.hasNextPage))
            } catch (_: Exception) {
                emit(MatchesEffect.Error(MatchesError.Generic))
            }
        }

        MatchesCommand.LoadNextPage -> flow {
            if (state.isLoadingMore || state.isLoading || state.isRefreshing || !state.hasMorePages) {
                return@flow
            }

            emit(MatchesEffect.LoadingMore)
            try {
                val result = fetchMatchesUseCase(page = state.currentPage + 1, perPage = PAGE_SIZE)
                emit(MatchesEffect.PageLoaded(result.matches, result.hasNextPage))
            } catch (_: Exception) {
                emit(MatchesEffect.PaginationError)
            }
        }

        MatchesCommand.Retry -> invoke(
            state = state.copy(error = null, isLoading = false, isRefreshing = false),
            command = MatchesCommand.Load
        )

        is MatchesCommand.MatchTapped -> flow {
            emit(
                MatchesEffect.NavigationRequested sendSideEffect
                    MatchesSideEffect.NavigateToDetail(command.match)
            )
        }
    }

    companion object {
        const val PAGE_SIZE = 50
        const val PAGINATION_THRESHOLD = 15
        private const val PAGE_SIZE_INITIAL_PAGE = 1
    }
}
