package com.voidx.csgo.feature.matches

import com.voidx.csgo.core.AppCoroutineDispatchers
import com.voidx.csgo.feature.matches.business.FetchMatchesUseCase
import com.voidx.csgo.feature.matches.business.MatchesInteractor
import com.voidx.csgo.feature.matches.presentation.MatchesReducer
import com.voidx.csgo.feature.matches.presentation.MatchesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val matchesModule = module {
    factory {
        FetchMatchesUseCase(
            matchRepository = get(),
            ioDispatcher = get<AppCoroutineDispatchers>().io,
        )
    }
    factory {
        MatchesInteractor(
            fetchMatchesUseCase = get(),
        )
    }
    factory { MatchesReducer() }
    viewModel {
        MatchesViewModel(
            interactor = get(),
            reducer = get(),
            runContext = get<AppCoroutineDispatchers>().main,
        )
    }
}
