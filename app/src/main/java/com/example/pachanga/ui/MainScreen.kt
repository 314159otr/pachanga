package com.example.pachanga.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.shared.Constants
import com.example.pachanga.shared.MyNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val startScreen = Constants.NavBarScreen.PLAYERS
    Scaffold(
        bottomBar = {
            MyNavigationBar(windowInsets = WindowInsets.navigationBars) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                Constants.NavBarScreen.entries.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                ImageVector.vectorResource(screen.resId),
                                contentDescription = screen.label
                            )
                        },
                        label = {
                            Text(screen.label)
                        }
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
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding() - WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
        ) {
            composable(Constants.NavBarScreen.PLAYERS.route) { FootballStatsScreen() }
            composable(Constants.NavBarScreen.MATCHES.route) { MatchesScreen() }
        }
    }
}