package com.voidx.csgo.data.mapper

import java.net.URI

internal fun String?.toPandaScoreThumbnailUrl(): String? {
    val value = this?.trim()?.takeIf(String::isNotEmpty) ?: return null

    return runCatching {
        val uri = URI(value)
        val path = uri.path ?: return value
        val lastSlashIndex = path.lastIndexOf('/')
        val filename = if (lastSlashIndex >= 0) {
            path.substring(lastSlashIndex + 1)
        } else {
            path
        }

        if (filename.isEmpty() || filename.startsWith("thumb_")) {
            return value
        }

        val thumbnailPath = buildString {
            if (lastSlashIndex >= 0) {
                append(path.substring(0, lastSlashIndex + 1))
            }
            append("thumb_")
            append(filename)
        }

        URI(
            uri.scheme,
            uri.userInfo,
            uri.host,
            uri.port,
            thumbnailPath,
            uri.query,
            uri.fragment,
        ).toString()
    }.getOrElse { value }
}
