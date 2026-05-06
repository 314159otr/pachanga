package com.example.pachanga.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pachanga.R
import com.example.pachanga.data.PachangaDbHelper
import com.example.pachanga.data.downloadDatabase
import com.example.pachanga.shared.Constants
import com.example.pachanga.shared.DataTable
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun FootballStatsScreen() {
    var reloadTrigger by remember { mutableIntStateOf(0) }
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState  = rememberScrollState()
    val context = LocalContext.current
    var headers by remember { mutableStateOf(emptyList<String>()) }
    val rows = remember { mutableStateListOf<Map<String, Any?>>() }
    var seasons by remember { mutableStateOf<List<Map<String, Any?>>>(emptyList()) }
    var selectedSeasonIndex by rememberSaveable { mutableIntStateOf(-1) }
    var downloadNeeded by remember { mutableStateOf(true) }
    var recentlyDownloaded by remember { mutableStateOf(false) }
    var isDownloading by remember { mutableStateOf(false) }

    LaunchedEffect(reloadTrigger) {
        if (PachangaDbHelper(context).databaseExists()){
            downloadNeeded = false
        }
        if (recentlyDownloaded) {
            selectedSeasonIndex = -1
            recentlyDownloaded = false
        }
        if (downloadNeeded) {
            seasons = emptyList()
        } else {
            seasons = PachangaDbHelper(context).queryTable(tableName = "match", distinct = true, columns = arrayOf("season") ).rows
            isDownloading = false
        }
        if (selectedSeasonIndex == -1 ) {
            selectedSeasonIndex = seasons.size - 1
        }
    }
    LaunchedEffect(selectedSeasonIndex, recentlyDownloaded) {
        if (selectedSeasonIndex  == -1) return@LaunchedEffect
        val season = seasons[selectedSeasonIndex]["season"]
        var table = PachangaDbHelper.DbTable(
            headers = emptyList(),
            rows = mutableListOf()
        )
        if (!downloadNeeded) {
            table = PachangaDbHelper(context).queryTable(tableName = "vw_player_stats", whereClause = "Temporada = $season")
        }
        headers = table.headers.toMutableList()
        rows.clear()
        rows.addAll(table.rows)
        horizontalScrollState.scrollTo(0)
        verticalScrollState.scrollTo(0)
    }
    val x = MaterialTheme.colorScheme.secondary
    Column (Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiary)){
        // Add a check to prevent rendering the DataTable composable until headers and players have data
        Box( modifier = Modifier.weight(1f).fillMaxSize().drawBehind {
            drawLine(
                color = x,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1f
            )
        }) {
            if (headers.isNotEmpty() && rows.isNotEmpty() ) {
                // We dont want to show the last column
                DataTable(
                    headers = headers.dropLast(1),
                    rows = rows,
                    horizontalScrollState = horizontalScrollState,
                    verticalScrollState = verticalScrollState,
                )
            }
            val coroutineScope = rememberCoroutineScope()
            val showFab by remember {
                derivedStateOf {
                    verticalScrollState.value == 0 && horizontalScrollState.value == 0
                }
            }
            if (isDownloading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            } else {
                androidx.compose.animation.AnimatedVisibility(
                    visible = showFab,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            headers = emptyList()
                            seasons = emptyList()

                            if (PachangaDbHelper(context).databaseExists()) {
                                File(context.filesDir.path + "/" + Constants.Database.DIR + "/" + Constants.Database.NAME).delete()
                            }
                            isDownloading = true
                            // Launch a coroutine to handle the download in the background
                            coroutineScope.launch {
                                val success = downloadDatabase(context)

                                if (success) {
                                    downloadNeeded = false
                                    reloadTrigger++
                                    recentlyDownloaded = true
                                }
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = "Download"
                        )
                    }
                }
            }
        }

        if (!isDownloading && seasons.isNotEmpty()){
            SeasonSelectorButton(
                selectedIndex = selectedSeasonIndex,
                onSelectedIndexChange = { newIndex -> selectedSeasonIndex = newIndex },
                seasons = seasons,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        drawLine(
                            color = x,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1f
                        )
                    },
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

    SingleChoiceSegmentedButtonRow(modifier.background(color = MaterialTheme.colorScheme.background)) {
        val colors = SegmentedButtonDefaults.colors(
                inactiveContainerColor = MaterialTheme.colorScheme.background,
                activeContainerColor = MaterialTheme.colorScheme.primary,
                disabledInactiveContainerColor = MaterialTheme.colorScheme.background,
            )
        SegmentedButton(
            colors = colors,
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            border = BorderStroke((-1).dp, MaterialTheme.colorScheme.secondary),
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
            colors = colors,
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            border = BorderStroke((-1).dp, MaterialTheme.colorScheme.secondary),
            onClick = {  },
            icon = {  },
            selected = true,
            label = { Text("Temporada " + seasons[selectedIndex]["season"].toString()) },
        )
        SegmentedButton(
            colors = colors,
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            border = BorderStroke((-1).dp, MaterialTheme.colorScheme.secondary),
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

