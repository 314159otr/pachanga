package com.example.pachanga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.data.copyDatabaseFromAssets
import com.example.pachanga.ui.FootballStatsScreen
import com.example.pachanga.ui.GreetingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        copyDatabaseFromAssets(this, "pachanga.db")

        setContent {
            PachangaApp()
        }
    }
}

@Preview
@Composable
fun PachangaApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "greeting") {
        composable("greeting") { GreetingScreen(navController) }
        composable("stats") { FootballStatsScreen() }
    }
}

