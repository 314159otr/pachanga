package com.example.pachanga.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.shared.Constants
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val startScreen = Constants.NavBarScreen.MATCHES
    var selectedScreen by rememberSaveable { mutableIntStateOf(startScreen.ordinal) }
    Scaffold(
        bottomBar = {
            NavigationBar(windowInsets = WindowInsets.navigationBars) {
                Constants.NavBarScreen.entries.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedScreen == index,
                        onClick = {
                            navController.navigate(screen.route)
                            selectedScreen = index
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
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding() - WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
        ) {
            composable(Constants.NavBarScreen.PLAYERS.route) { FootballStatsScreen() }
            composable(Constants.NavBarScreen.MATCHES.route) { MatchesScreen() }
        }
    }
}