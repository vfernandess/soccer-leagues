package com.voidx.csgo.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CsgoColorScheme = darkColorScheme(
    background = AppBackground,
    surface = CardBackground,
    primary = LiveBadge,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun CsgoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CsgoColorScheme,
        content = content,
    )
}
