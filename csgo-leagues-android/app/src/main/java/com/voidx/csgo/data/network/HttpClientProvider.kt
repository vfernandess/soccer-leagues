package com.voidx.csgo.data.network

import android.util.Log
import com.voidx.csgo.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val KTOR_LOG_TAG = "KtorHttp"

object HttpClientProvider {
    fun create(
        json: Json = AppJson.instance,
        tokenProvider: () -> String,
    ): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = tokenProvider(),
                        refreshToken = "",
                    )
                }
            }
        }

        install(DefaultRequest) {
            url("https://api.pandascore.co")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = AndroidLogcatLogger
                level = LogLevel.BODY
            }
        }
    }
}

private object AndroidLogcatLogger : Logger {
    override fun log(message: String) {
        message.chunked(MAX_LOG_LENGTH).forEach { chunk ->
            Log.d(KTOR_LOG_TAG, chunk)
        }
    }

    private const val MAX_LOG_LENGTH = 4_000
}
