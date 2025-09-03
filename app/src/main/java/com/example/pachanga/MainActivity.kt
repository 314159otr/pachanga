package com.example.pachanga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.shared.Constants
import com.example.pachanga.ui.FootballStatsScreen
import com.example.pachanga.ui.GreetingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PachangaApp()
        }
    }
}

@Preview
@Composable
fun PachangaApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Constants.Nav.GREETING.route) {
        composable(Constants.Nav.GREETING.route) { GreetingScreen(navController) }
        composable(Constants.Nav.PLAYERS_STATS.route) { FootballStatsScreen() }
    }
}
