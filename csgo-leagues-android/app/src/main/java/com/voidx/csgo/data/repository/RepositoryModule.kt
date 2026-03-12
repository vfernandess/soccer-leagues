package com.voidx.csgo.data.repository

import com.voidx.csgo.data.repository.impl.MatchRepositoryImpl
import com.voidx.csgo.data.repository.impl.PlayerRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MatchRepository> { MatchRepositoryImpl(apiService = get()) }
    single<PlayerRepository> { PlayerRepositoryImpl(apiService = get()) }
}
