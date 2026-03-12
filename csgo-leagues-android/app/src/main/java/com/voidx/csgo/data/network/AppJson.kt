package com.voidx.csgo.data.network

import com.voidx.csgo.domain.entity.LocalDateTimeIsoSerializer
import java.time.LocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

object AppJson {
    val instance: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, LocalDateTimeIsoSerializer)
        }
    }
}
