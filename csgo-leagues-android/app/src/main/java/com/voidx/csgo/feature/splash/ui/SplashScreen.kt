package com.voidx.csgo.feature.splash.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voidx.csgo.R
import com.voidx.csgo.core.theme.AppBackground
import com.voidx.csgo.core.theme.DS
import com.voidx.csgo.feature.splash.presentation.SplashCommand
import com.voidx.csgo.feature.splash.presentation.SplashViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToMatches: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val alpha by animateFloatAsState(
        targetValue = if (state.isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "splash-alpha",
    )

    LaunchedEffect(Unit) {
        viewModel.dispatch(SplashCommand.Start)
    }

    LaunchedEffect(state.isVisible) {
        if (!state.isVisible) {
            delay(400L)
            onNavigateToMatches()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.fuze_logo),
            contentDescription = stringResource(R.string.splash_logo_content_description),
            modifier = Modifier
                .size(DS.Icon.i28)
                .alpha(alpha),
        )
    }
}
