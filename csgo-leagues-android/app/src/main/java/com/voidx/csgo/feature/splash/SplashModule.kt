package com.voidx.csgo.feature.splash

import com.voidx.csgo.core.AppCoroutineDispatchers
import com.voidx.csgo.feature.splash.business.SplashInteractor
import com.voidx.csgo.feature.splash.presentation.SplashReducer
import com.voidx.csgo.feature.splash.presentation.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    factory { SplashInteractor() }
    factory { SplashReducer() }
    viewModel {
        SplashViewModel(
            interactor = get(),
            reducer = get(),
            runContext = get<AppCoroutineDispatchers>().main,
        )
    }
}
