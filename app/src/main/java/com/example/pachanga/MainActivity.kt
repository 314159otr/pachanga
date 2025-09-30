package com.example.pachanga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.shared.Constants
import com.example.pachanga.ui.GreetingScreen
import com.example.pachanga.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT,
            )
        )
        setContent {
            val rootNavController = rememberNavController()

            NavHost(
                navController = rootNavController,
                startDestination = Constants.RootRoutes.GREETING) {
                composable(Constants.RootRoutes.GREETING) { GreetingScreen(rootNavController) }
                composable(Constants.RootRoutes.MAIN_CONTAINER) { MainScreen() }
            }
        }
    }
}