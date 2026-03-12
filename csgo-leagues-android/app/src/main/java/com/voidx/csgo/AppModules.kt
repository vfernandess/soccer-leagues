package com.voidx.csgo

import com.voidx.csgo.data.network.networkModule
import com.voidx.csgo.data.repository.repositoryModule
import com.voidx.csgo.core.coroutineDispatchersModule
import com.voidx.csgo.feature.matchdetail.matchDetailModule
import com.voidx.csgo.feature.matches.matchesModule
import com.voidx.csgo.feature.splash.splashModule
import org.koin.core.module.Module

fun appModules(tokenProvider: () -> String): List<Module> =
    listOf(
        coroutineDispatchersModule,
        networkModule(tokenProvider = tokenProvider),
        repositoryModule,
        splashModule,
        matchesModule,
        matchDetailModule,
    )
