package com.voidx.csgo

import android.app.Application
import com.voidx.csgo.core.Secrets
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModules(tokenProvider = Secrets::getApiToken))
        }
    }
}
