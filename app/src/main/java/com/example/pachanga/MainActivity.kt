package com.example.pachanga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import com.example.pachanga.ui.MainScreen
import com.example.pachanga.ui.theme.PachangaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            enableEdgeToEdge(
                statusBarStyle = if (isSystemInDarkTheme()) {
                    SystemBarStyle.dark(
                        scrim = android.graphics.Color.TRANSPARENT
                    )
                } else {
                    SystemBarStyle.light(
                        scrim = android.graphics.Color.TRANSPARENT,
                        darkScrim = android.graphics.Color.TRANSPARENT
                    )
                }
            )
            PachangaTheme(dynamicColor = false) {
                MainScreen()
            }
        }
    }
}