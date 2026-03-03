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
    primary = Dark3,
    onPrimary = Dark2,
    primaryContainer = Dark3,
    onPrimaryContainer = Dark2,

    secondary = Dark4,
    onSecondary = Dark2,
    secondaryContainer = Dark3,
    onSecondaryContainer = Dark2,

    tertiary = Dark5,
    onTertiary = Dark2,

    background = Dark1,
    onBackground = Dark2,
    surface = Dark1,
    onSurface = Dark2
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