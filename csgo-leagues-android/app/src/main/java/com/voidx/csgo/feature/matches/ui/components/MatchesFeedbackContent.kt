package com.voidx.csgo.feature.matches.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.voidx.csgo.R
import com.voidx.csgo.core.theme.AppBackground
import com.voidx.csgo.core.theme.AppTextStyle
import com.voidx.csgo.core.theme.DS

@Composable
fun ErrorMatchesContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(DS.Size.s6),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = AppTextStyle.FeedbackMessage
        )
        Text(
            text = stringResource(R.string.matches_retry),
            style = AppTextStyle.FeedbackAction,
            modifier = Modifier
                .padding(top = DS.Size.s3)
                .clickable(onClick = onRetry)
        )
    }
}

@Composable
fun EmptyMatchesContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(DS.Size.s6),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.matches_empty),
            style = AppTextStyle.FeedbackMessage
        )
    }
}
