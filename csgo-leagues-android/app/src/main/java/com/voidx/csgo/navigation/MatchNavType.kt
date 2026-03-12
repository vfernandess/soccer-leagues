package com.voidx.csgo.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.voidx.csgo.data.network.AppJson
import com.voidx.csgo.domain.entity.Match
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

object MatchNavType : NavType<Match>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Match? =
        deserializeOrNull(bundle.getString(key))

    override fun parseValue(value: String): Match =
        AppJson.instance.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: Match): String =
        Uri.encode(AppJson.instance.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: Match) {
        bundle.putString(key, AppJson.instance.encodeToString(value))
    }

    fun deserializeOrNull(value: String?): Match? = value?.let { encodedValue ->
        runCatching {
            AppJson.instance.decodeFromString<Match>(Uri.decode(encodedValue))
        }.getOrNull()
    }
}
