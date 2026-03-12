package com.voidx.csgo.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object AppShape {
    val Card = RoundedCornerShape(DS.Radius.r4)
    val PlayerPhoto = RoundedCornerShape(DS.Radius.r2)

    val PlayerRowLeft = RoundedCornerShape(
        topStart = 0.dp,
        bottomStart = 0.dp,
        topEnd = DS.Radius.r3,
        bottomEnd = DS.Radius.r3,
    )

    val PlayerRowRight = RoundedCornerShape(
        topStart = DS.Radius.r3,
        bottomStart = DS.Radius.r3,
        topEnd = 0.dp,
        bottomEnd = 0.dp,
    )

    val Badge = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomEnd = 0.dp,
        bottomStart = DS.Radius.r4,
    )
}
