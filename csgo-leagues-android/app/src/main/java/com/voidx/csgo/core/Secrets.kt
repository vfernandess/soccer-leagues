package com.voidx.csgo.core

object Secrets {
    init {
        System.loadLibrary("native-secrets")
    }

    external fun getApiToken(): String
}
