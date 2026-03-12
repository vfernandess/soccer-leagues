package com.voidx.csgo.core.arch.compose.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <E> OnEffect(effect: Flow<E>, block: (E) -> Unit) {
    LaunchedEffect(Unit) {
        effect.collect(block)
    }
}
