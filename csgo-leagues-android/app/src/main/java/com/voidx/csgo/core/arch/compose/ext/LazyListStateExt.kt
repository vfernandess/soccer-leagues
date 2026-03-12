package com.voidx.csgo.core.arch.compose.ext

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

private typealias IsScrolling = Boolean
private typealias VisibleIndex = Int

@Composable
fun LazyListState.OnFirstVisibleIndexChanged(
    onVisibleIndexChanged: (VisibleIndex) -> Unit
) {
    LaunchedEffect(key1 = this) {
        snapshotFlow { this@OnFirstVisibleIndexChanged.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect(onVisibleIndexChanged)
    }
}

@Composable
fun LazyListState.OnScrollStateChanged(
    onScrollStateChanged: (IsScrolling) -> Unit
) {
    LaunchedEffect(key1 = this) {
        snapshotFlow { this@OnScrollStateChanged.isScrollInProgress }
            .distinctUntilChanged()
            .collect(onScrollStateChanged)
    }
}

@Composable
fun LazyListState.OnApproachingListEnd(
    enable: Boolean = true,
    endOffset: Int = 0,
    callback: () -> Unit,
) {
    check(endOffset >= 0) { "endOffset should be at least 0!" }

    val isApproachingEnd by remember(this, enable, endOffset) {
        derivedStateOf {
            val lastIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val totalItems = layoutInfo.totalItemsCount - 1
            enable && isScrollInProgress && totalItems >= 0 && lastIndex >= (totalItems - endOffset)
        }
    }

    LaunchedEffect(isApproachingEnd) {
        if (isApproachingEnd) {
            callback()
        }
    }
}
