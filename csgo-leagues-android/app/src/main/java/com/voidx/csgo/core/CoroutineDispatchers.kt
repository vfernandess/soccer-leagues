package com.voidx.csgo.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

data class AppCoroutineDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)

val coroutineDispatchersModule = module {
    single {
        AppCoroutineDispatchers(
            main = Dispatchers.Main.immediate,
            io = Dispatchers.IO,
            default = Dispatchers.Default,
        )
    }
}
