package com.example.pachanga.ui

import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pachanga.data.PachangaDbHelper
import com.example.pachanga.data.PlayerStats

private val CELL_WIDTH = 120.dp  // tweak to taste

@Composable
fun DataTable1(
    headers: List<String>,
    rows: List<PlayerStats>
) {
    val hScroll = rememberScrollState()

    Box(Modifier.fillMaxSize()) {
        // Horizontal scrolling container
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(hScroll)
        ) {
            // Everything inside this Column will scroll horizontally together
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .background(Color(0xFFEFEFEF))
                        .border(1.dp, Color(0xFFDDDDDD))
                        .padding(vertical = 8.dp)
                ) {
                    headers.forEach { header ->
                        Cell(
                            text = header,
                            bold = true,
                            modifier = Modifier.width(CELL_WIDTH)
                        )
                    }
                }

                Divider()

                // Data (vertical scroll handled by LazyColumn)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .border(1.dp, Color(0xFFEEEEEE))
                ) {
                    items(rows) { p ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .padding(vertical = 6.dp)
                        ) {
                            rowValues(p).forEach { value ->
                                Cell(
                                    text = value,
                                    modifier = Modifier.width(CELL_WIDTH)
                                )
                            }
                        }
                        Divider(color = Color(0xFFEEEEEE))
                    }
                }
            }
        }
    }
}

// Helper to turn a PlayerStats into a list of strings in the same order as headers
private fun rowValues(p: PlayerStats): List<String> = listOf(
    p.id.toString(),
    p.nickname,
    p.name,
    p.lastnames,
    p.goals.toString(),
    p.own_goals.toString(),
    p.matches.toString(),
    p.puskas.toString()
)

@Composable
private fun Cell(
    text: String,
    bold: Boolean = false,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Preview
@Composable
fun FootballStatsScreen() {
    val context = LocalContext.current
    var headers by remember { mutableStateOf(emptyList<String>()) }
    val players = remember { mutableStateListOf<PlayerStats>() }

    // Use LaunchedEffect to perform the database query
    LaunchedEffect(Unit) {
        val table = PachangaDbHelper(context).queryTable("players_stats")
        headers = table.headers.toMutableList()
        val playerList = table.rows.map { row ->
            PlayerStats(
                id = row["id"] as Int,
                nickname = row["nickname"] as String,
                name = row["name"] as String,
                lastnames = row["lastnames"] as String,
                goals = row["goals"] as Int,
                own_goals = row["own_goals"] as Int,
                matches = row["matches"] as Int,
                puskas = row["puskas"] as Int,
            )
        }
        players.clear()
        players.addAll(playerList)
    }

    Column (Modifier.fillMaxSize()){
        // Add a check to prevent rendering the DataTable2 composable until headers and players have data
        if (headers.isNotEmpty() && players.isNotEmpty()) {
            DataTable2(headers = headers, rows = players, modifier = Modifier.weight(1f))
        } else {
            // Optional: Show a loading indicator or a message while the data is loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading stats...")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    val sorted = players.sortedByDescending { it.name }
                    players.clear()
                    players.addAll(sorted)
                }
            ) {
                Text("Descending name")
            }
            Button(
                onClick = {
                    val sorted = players.sortedBy { it.name }
                    players.clear()
                    players.addAll(sorted)
                }
            ) {
                Text("Ascending name")
            }
        }
    }
}

@Composable
fun DataTable2(
    headers: List<String>,
    rows: MutableList<PlayerStats>,
    modifier: Modifier
) {
    val fixedColumnWidth = 120.dp
    val normalCellWidth = 120.dp
    val headerHeight = 48.dp
    val rowHeight = 48.dp
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState  = rememberScrollState()

    Column (modifier = modifier){
        Row (modifier = Modifier.background(Color.LightGray)){
            // first header
            Box(modifier = Modifier
                .width(fixedColumnWidth)
                .height(headerHeight)
                .background(Color.Gray)
                .padding(8.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text = headers.first(), fontWeight = FontWeight.Bold)
            }
            // headers
            Row(modifier = Modifier
                .horizontalScroll(horizontalScrollState)
            ){
                headers.drop(1).forEach { header ->
                    Box(modifier = Modifier
                        .width(normalCellWidth)
                        .height(headerHeight)
                        .background(Color.LightGray)
                        .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = header, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Row (modifier = Modifier.weight(1f)){
            // first column
            Column (modifier = Modifier
                .width(fixedColumnWidth)
                .verticalScroll(verticalScrollState)
                .background(Color.LightGray)
            ) {
                rows.forEach { row ->
                    Box(
                        modifier = Modifier
                            .width(fixedColumnWidth)
                            .height(rowHeight)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = row.id.toString(), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            // columns
            Box(modifier = Modifier
                .weight(1f)
                .horizontalScroll(horizontalScrollState)
                .verticalScroll(verticalScrollState)
            ){
                Column {
                    rows.forEach { row ->
                        Row {
                            rowValues(row).drop(1).forEach { cell ->
                                Box(modifier = Modifier
                                    .width(normalCellWidth)
                                    .height(rowHeight)
                                    .background(Color.White)
                                    .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = cell.toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
