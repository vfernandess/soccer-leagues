package com.voidx.csgo.data.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PandaScoreImageUrlTest {

    @Test
    fun `returns null for null or blank values`() {
        assertNull(null.toPandaScoreThumbnailUrl())
        assertNull("   ".toPandaScoreThumbnailUrl())
    }

    @Test
    fun `prefixes the filename with thumb`() {
        val url = "https://cdn.pandascore.co/images/team/image/123/logo.png"

        assertEquals(
            "https://cdn.pandascore.co/images/team/image/123/thumb_logo.png",
            url.toPandaScoreThumbnailUrl(),
        )
    }

    @Test
    fun `preserves query and fragment`() {
        val url = "https://cdn.pandascore.co/images/player/image/77/avatar.jpg?size=original#bio"

        assertEquals(
            "https://cdn.pandascore.co/images/player/image/77/thumb_avatar.jpg?size=original#bio",
            url.toPandaScoreThumbnailUrl(),
        )
    }

    @Test
    fun `leaves thumb urls unchanged`() {
        val url = "https://cdn.pandascore.co/images/league/image/99/thumb_logo.png"

        assertEquals(url, url.toPandaScoreThumbnailUrl())
    }
}
