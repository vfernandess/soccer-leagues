package com.voidx.csgo.data.network

import org.koin.dsl.module

fun networkModule(tokenProvider: () -> String) = module {
    single { AppJson.instance }
    single { HttpClientProvider.create(json = get(), tokenProvider = tokenProvider) }
    single { ApiService(client = get()) }
}
