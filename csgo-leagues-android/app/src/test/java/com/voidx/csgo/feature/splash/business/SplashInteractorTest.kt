package com.voidx.csgo.feature.splash.business

import app.cash.turbine.test
import com.voidx.csgo.feature.splash.presentation.SplashCommand
import com.voidx.csgo.feature.splash.presentation.SplashEffect
import com.voidx.csgo.feature.splash.presentation.SplashState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashInteractorTest {

    @Test
    fun `start waits two seconds then completes`() = runTest {
        val interactor = SplashInteractor()

        interactor.invoke(SplashState(isVisible = true), SplashCommand.Start).test {
            advanceTimeBy(2_000)
            assertEquals(SplashEffect.TimerCompleted, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `start does nothing when splash is already hidden`() = runTest {
        val interactor = SplashInteractor()

        interactor.invoke(SplashState(isVisible = false), SplashCommand.Start).test {
            awaitComplete()
        }
    }
}
