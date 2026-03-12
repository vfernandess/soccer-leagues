package com.voidx.csgo.feature.matchdetail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.voidx.csgo.R
import com.voidx.csgo.domain.entity.Player
import com.voidx.csgo.core.theme.AppShape
import com.voidx.csgo.core.theme.AppTextStyle
import com.voidx.csgo.core.theme.CardBackground
import com.voidx.csgo.core.theme.DS
import com.voidx.csgo.core.theme.Placeholder
import androidx.compose.material3.Text

enum class PlayerRowAlignment {
    LEFT,
    RIGHT,
}

@Composable
fun PlayerRow(
    player: Player,
    alignment: PlayerRowAlignment,
    modifier: Modifier = Modifier,
) {
    val shape = when (alignment) {
        PlayerRowAlignment.LEFT -> AppShape.PlayerRowLeft
        PlayerRowAlignment.RIGHT -> AppShape.PlayerRowRight
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(DS.Component.playerRowHeight)
                .padding(top = DS.Component.playerRowBgOffset)
                .background(CardBackground, shape),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(DS.Component.playerRowHeight + DS.Component.playerRowBgOffset),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = when (alignment) {
                PlayerRowAlignment.LEFT -> Arrangement.End
                PlayerRowAlignment.RIGHT -> Arrangement.Start
            },
        ) {
            if (alignment == PlayerRowAlignment.RIGHT) {
                PlayerPhoto(imageUrl = player.imageUrl)
                Spacer(modifier = Modifier.width(DS.Size.s4))
            }

            Column(
                modifier = Modifier.width(DS.Component.playerTextWidth),
                horizontalAlignment = when (alignment) {
                    PlayerRowAlignment.LEFT -> Alignment.End
                    PlayerRowAlignment.RIGHT -> Alignment.Start
                },
            ) {
                val fallbackNickname = stringResource(R.string.match_detail_unknown_player)
                Text(
                    text = player.nickname?.ifBlank { fallbackNickname } ?: fallbackNickname,
                    style = AppTextStyle.PlayerNickname,
                    maxLines = 1,
                )

                player.fullName?.takeIf(String::isNotBlank)?.let {
                    Spacer(modifier = Modifier.height(DS.Size.s2))
                    Text(
                        text = it,
                        style = AppTextStyle.PlayerName,
                        maxLines = 1,
                    )
                }
            }

            if (alignment == PlayerRowAlignment.LEFT) {
                Spacer(modifier = Modifier.width(DS.Size.s4))
                PlayerPhoto(imageUrl = player.imageUrl)
            }
        }
    }
}

@Composable
private fun PlayerPhoto(imageUrl: String?) {
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.match_detail_player_photo_content_description),
        modifier = Modifier
            .size(DS.Icon.i12)
            .clip(AppShape.PlayerPhoto)
            .background(Placeholder),
        contentScale = ContentScale.Crop,
        placeholder = ColorPainter(Placeholder),
        error = ColorPainter(Placeholder),
    )
}
