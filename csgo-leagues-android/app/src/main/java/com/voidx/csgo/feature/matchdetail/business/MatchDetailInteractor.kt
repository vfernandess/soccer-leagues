package com.voidx.csgo.feature.matchdetail.business

import com.voidx.csgo.core.arch.Interactor
import com.voidx.csgo.feature.matchdetail.business.FetchPlayersUseCase
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailCommand
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailEffect
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class MatchDetailInteractor(
    private val fetchPlayersUseCase: FetchPlayersUseCase,
) : Interactor<MatchDetailState, MatchDetailCommand, MatchDetailEffect> {

    override suspend fun invoke(
        state: MatchDetailState,
        command: MatchDetailCommand,
    ): Flow<MatchDetailEffect> = when (command) {
        is MatchDetailCommand.LoadPlayers -> flow {
            if (state.isLoading) {
                return@flow
            }

            emit(MatchDetailEffect.Init(command.match))
            emit(MatchDetailEffect.Loading)

            val team1Id = command.match.teams.getOrNull(0)?.id
            val team2Id = command.match.teams.getOrNull(1)?.id

            coroutineScope {
                val team1Result = async {
                    team1Id?.let {
                        runCatching { fetchPlayersUseCase(it) }
                    }
                }
                val team2Result = async {
                    team2Id?.let {
                        runCatching { fetchPlayersUseCase(it) }
                    }
                }

                team1Result.await()?.fold(
                    onSuccess = { emit(MatchDetailEffect.Team1PlayersLoaded(it)) },
                    onFailure = { emit(MatchDetailEffect.Team1Error) },
                )
                team2Result.await()?.fold(
                    onSuccess = { emit(MatchDetailEffect.Team2PlayersLoaded(it)) },
                    onFailure = { emit(MatchDetailEffect.Team2Error) },
                )
            }

            emit(MatchDetailEffect.LoadingCompleted)
        }

        MatchDetailCommand.Retry -> {
            val match = state.match ?: return emptyFlow()
            invoke(
                state = state.copy(
                    team1Players = emptyList(),
                    team2Players = emptyList(),
                    team1HasError = false,
                    team2HasError = false,
                    isLoading = false,
                ),
                command = MatchDetailCommand.LoadPlayers(match),
            )
        }
    }
}