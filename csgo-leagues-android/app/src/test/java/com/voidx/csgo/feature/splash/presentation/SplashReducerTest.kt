package com.voidx.csgo.feature.splash.presentation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SplashReducerTest {

    @Test
    fun `timer completion hides the splash`() {
        val reducer = SplashReducer()

        val result = reducer.reduce(
            SplashEffect.TimerCompleted,
            SplashState(isVisible = true),
        )

        assertEquals(false, result.isVisible)
    }
}
