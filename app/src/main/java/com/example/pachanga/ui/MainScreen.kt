package com.example.pachanga.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.data.PachangaDbHelper
import com.example.pachanga.shared.Constants
import com.example.pachanga.shared.MyNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val startScreen = Constants.NavBarScreen.PLAYERS
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            MyNavigationBar(Modifier.windowInsetsPadding(WindowInsets.systemBars.only(
                WindowInsetsSides.Bottom)).height(50.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Constants.NavBarScreen.entries.forEach { screen ->
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            if (PachangaDbHelper(context).databaseExists()){
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            val tint = if (currentRoute == screen.route){
                                MaterialTheme.colorScheme.onBackground } else { MaterialTheme.colorScheme.secondary}
                            val resId = if (currentRoute == screen.route) {screen.resIdSelected} else {screen.resId}
                            Icon(
                                ImageVector.vectorResource(resId),
                                contentDescription = screen.label,
                                tint = tint
                            )
                        },
                    )
                }

            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Constants.NavBarScreen.PLAYERS.route) { FootballStatsScreen() }
            composable(Constants.NavBarScreen.MATCHES.route) { MatchesScreen() }
        }
    }
}