package com.voidx.csgo.feature.matchdetail

import com.voidx.csgo.core.AppCoroutineDispatchers
import com.voidx.csgo.feature.matchdetail.business.FetchPlayersUseCase
import com.voidx.csgo.feature.matchdetail.business.MatchDetailInteractor
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailReducer
import com.voidx.csgo.feature.matchdetail.presentation.MatchDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val matchDetailModule = module {
    factory {
        FetchPlayersUseCase(
            playerRepository = get(),
            ioDispatcher = get<AppCoroutineDispatchers>().io,
        )
    }
    factory {
        MatchDetailInteractor(
            fetchPlayersUseCase = get(),
        )
    }
    factory { MatchDetailReducer() }
    viewModel {
        MatchDetailViewModel(
            interactor = get(),
            reducer = get(),
            runContext = get<AppCoroutineDispatchers>().main,
        )
    }
}
