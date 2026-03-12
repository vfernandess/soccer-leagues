package com.voidx.csgo.core.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyle {
    val ScreenTitle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
    )
    val NavTitle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White,
    )
    val Badge = TextStyle(
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
    val MatchTime = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
    val TeamName = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White,
    )
    val Vs = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = TextSecondary,
    )
    val League = TextStyle(
        fontSize = 8.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White,
    )
    val PlayerNickname = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
    val PlayerName = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = TextSubtitle,
    )
    val FeedbackMessage = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = Color.White,
    )
    val FeedbackAction = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
    )
}
