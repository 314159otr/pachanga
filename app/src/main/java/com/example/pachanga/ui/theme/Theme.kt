package com.example.pachanga.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Light3,
    onPrimary = Light2,
    primaryContainer = Light3,
    onPrimaryContainer = Light2,

    secondary = Light4,
    onSecondary = Light2,
    secondaryContainer = Light3,
    onSecondaryContainer = Light2,

    tertiary = Light5,
    onTertiary = Light2,

    background = Light1,
    onBackground = Light2,
    surface = Light1,
    onSurface = Light2
)


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
    surface = DarkBackgroundColor,
    onSurface = DarkTextColor
)

@Composable
fun PachangaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}