package com.example.pachanga.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.pachanga.data.PachangaDbHelper
import com.example.pachanga.shared.DataTable

@Composable
fun FootballStatsScreen() {
    val context = LocalContext.current
    var headers by remember { mutableStateOf(emptyList<String>()) }
    val rows = remember { mutableStateListOf<Map<String, Any?>>() }

    // Use LaunchedEffect to perform the database query
    LaunchedEffect(Unit) {
        val table = PachangaDbHelper(context).queryTable("vw_player_stats_season_2")
        headers = table.headers.toMutableList()
        rows.clear()
        rows.addAll(table.rows)
    }

    Column (Modifier.safeDrawingPadding().fillMaxSize()){
        // Add a check to prevent rendering the DataTable composable until headers and players have data
        if (headers.isNotEmpty() && rows.isNotEmpty()) {
            DataTable(headers = headers, rows = rows, modifier = Modifier.weight(1f))
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading stats...")
            }
        }
    }
}

