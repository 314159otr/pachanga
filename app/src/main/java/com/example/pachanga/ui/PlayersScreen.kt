package com.example.pachanga.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pachanga.data.PachangaDbHelper
import com.example.pachanga.shared.DataTable

@Composable
fun FootballStatsScreen() {
    val context = LocalContext.current
    var headers by remember { mutableStateOf(emptyList<String>()) }
    val rows = remember { mutableStateListOf<Map<String, Any?>>() }
    var seasons by remember { mutableStateOf<List<Map<String, Any?>>>(emptyList()) }
    var selectedSeasonIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        seasons = PachangaDbHelper(context).queryTable(tableName = "match", distinct = true, columns = arrayOf("season") ).rows
        selectedSeasonIndex = seasons.size - 1
    }
    LaunchedEffect(selectedSeasonIndex) {
        if (selectedSeasonIndex  == -1) return@LaunchedEffect
        val season = seasons[selectedSeasonIndex]["season"]
        val table = PachangaDbHelper(context).queryTable(tableName = "vw_player_stats", whereClause = "Temporada = $season")
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
        if (seasons.isNotEmpty()){
            SeasonSelectorButton(
                selectedIndex = selectedSeasonIndex,
                onSelectedIndexChange = { newIndex -> selectedSeasonIndex = newIndex },
                seasons = seasons,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 40.dp)
                    .padding(horizontal = 4.dp),
            )
        }
    }
}
@Composable
fun SeasonSelectorButton(
    seasons: List<Map<String,Any?>>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val prevIndex = selectedIndex - 1
    val nextIndex = selectedIndex + 1

    SingleChoiceSegmentedButtonRow(modifier) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            onClick = {onSelectedIndexChange(prevIndex)},
            selected = false,
            enabled = prevIndex >= 0,
            label = {
                seasons.getOrNull(prevIndex)?.let {
                    Text("Temporada " + seasons[prevIndex]["season"].toString() )
                }
            },
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            onClick = {  },
            icon = {  },
            selected = true,
            label = { Text("Temporada " + seasons[selectedIndex]["season"].toString()) },
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            onClick = { onSelectedIndexChange(nextIndex) },
            selected = false,
            enabled = nextIndex < seasons.size,
            label = {
                seasons.getOrNull(nextIndex)?.let {
                    Text("Temporada " + seasons[nextIndex]["season"].toString())
                }
            },
        )
    }
}

