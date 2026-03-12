package com.voidx.csgo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.voidx.csgo.core.theme.CsgoTheme
import com.voidx.csgo.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val appBackground = Color.parseColor("#161621")

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(appBackground),
            navigationBarStyle = SystemBarStyle.dark(appBackground)
        )

        setContent {
            CsgoTheme {
                AppNavigation()
            }
        }
    }
}