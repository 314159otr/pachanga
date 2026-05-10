package com.example.pachanga.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccentColor,
    onPrimary = DarkTextColor,
    primaryContainer = DarkAccentColor,
    onPrimaryContainer = DarkTextColor,

    secondary = DarkDetailColor,
    onSecondary = DarkTextColor,
    secondaryContainer = DarkAccentColor,
    onSecondaryContainer = DarkTextColor,

    tertiary = DarkMainColor,
    onTertiary = DarkTextColor,

    background = DarkBackgroundColor,
    onBackground = DarkTextColor,
    surface = DarkBlack,
    onSurface = DarkTextColor
)

@Composable
fun PachangaTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window

        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
    }
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}