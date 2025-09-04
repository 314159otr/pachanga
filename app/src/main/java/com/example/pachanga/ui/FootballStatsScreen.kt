package com.example.pachanga.ui

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pachanga.data.PachangaDbHelper
import com.example.pachanga.data.PlayerStats
import kotlin.math.roundToInt

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

    Column (Modifier.safeDrawingPadding().fillMaxSize()){
        // Add a check to prevent rendering the DataTable2 composable until headers and players have data
        if (headers.isNotEmpty() && players.isNotEmpty()) {
            DataTable(headers = headers, rows = players, modifier = Modifier.weight(1f))
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
fun DataTable(
    headers: List<String>,
    rows: MutableList<PlayerStats>,
    modifier: Modifier
) {
    val context = LocalContext.current
    val headerHeight = 48.dp
    val rowHeight = 48.dp
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState  = rememberScrollState()
    val widths = IntArray(headers.size)
    val alignments = Array(headers.size){ index ->
        if (rowValues(rows[0])[index].toDoubleOrNull() != null){
            Alignment.CenterEnd
        }else{
            Alignment.CenterStart
        }
    }
    headers.forEachIndexed { index, header ->
        widths[index] = header.getStringWidthInDp(context)
    }
    rows.forEach { row ->
        rowValues(row).forEachIndexed { index, it ->
            val length = it.getStringWidthInDp(context)
            if (length > widths[index]) {
                widths[index] = length
            }
        }
    }

    Column (modifier = modifier){
        Row (modifier = Modifier.background(Color.LightGray)){
            // first header
            Box(modifier = Modifier
                .width(widths[0].dp + 16.dp)
                .height(headerHeight)
                .background(Color.Gray)
                .padding(8.dp),
                contentAlignment = alignments[0]
            ){
                Text(text = headers.first(), fontWeight = FontWeight.Bold)
            }
            // headers
            Row(modifier = Modifier
                .horizontalScroll(horizontalScrollState)
            ){
                headers.drop(1).forEachIndexed { index, header ->
                    Box(modifier = Modifier
                        .width(widths[index + 1].dp + 16.dp)
                        .height(headerHeight)
                        .background(Color.LightGray)
                        .padding(8.dp),
                        contentAlignment = alignments[index + 1]
                    ){
                        Text(text = header, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Row (modifier = Modifier.weight(1f)){
            // first column
            Column (modifier = Modifier
                .width(widths[0].dp + 16.dp)
                .verticalScroll(verticalScrollState)
                .background(Color.LightGray)
            ) {
                rows.forEach { row ->
                    Box(
                        modifier = Modifier
                            .width(widths[0].dp + 16.dp)
                            .height(rowHeight)
                            .padding(8.dp),
                        contentAlignment = alignments[0]
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
                            rowValues(row).drop(1).forEachIndexed { index, cell ->
                                Box(modifier = Modifier
                                    .width(widths[index + 1].dp + 16.dp)
                                    .height(rowHeight)
                                    .background(Color.White)
                                    .padding(8.dp),
                                    contentAlignment = alignments[index + 1]
                                ) {
                                    Text(text = cell)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
/**
 * Calculates the visual width of a string in density-independent pixels (dp).
 * This is useful for dynamically sizing UI elements to fit text content.
 *
 * @param context The Android context, needed to access display metrics.
 * @param text The string whose width needs to be measured.
 * @param textSizeSp The size of the text in sp (scale-independent pixels). Defaults to 16sp for better compatibility with Jetpack Compose.
 * @return The width of the string in dp.
 */
fun getStringWidthInDp(context: Context, text: String, textSizeSp: Float = 16f): Int {
    // Get the screen's display metrics (like density).
    val displayMetrics = context.resources.displayMetrics

    // Convert the given text size from sp to pixels.
    // This is crucial because Paint.measureText() works with pixels.
    val textSizePx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        textSizeSp,
        displayMetrics
    )

    // Create a Paint object to measure the text.
    val paint = Paint()
    paint.textSize = textSizePx

    // Measure the string's width in pixels.
    val stringWidthPx = paint.measureText(text)

    // Convert the pixel width to dp.
    val density = displayMetrics.density
    val stringWidthDp = stringWidthPx / density

    // Round the result to the nearest integer dp value before returning.
    return stringWidthDp.roundToInt()
}

/**
 * An extension function on String to make the function call more concise.
 *
 * Example usage: "Hello World".getStringWidthInDp(context)
 *
 * @param context The Android context.
 * @param textSizeSp The text size in sp.
 * @return The width of the string in dp.
 */
fun String.getStringWidthInDp(context: Context, textSizeSp: Float = 16f): Int {
    return getStringWidthInDp(context, this, textSizeSp)
}
