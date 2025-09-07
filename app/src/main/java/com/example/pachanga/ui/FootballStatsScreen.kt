package com.example.pachanga.ui

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
        val table = PachangaDbHelper(context).queryTable("vw_player_stats")
        headers = table.headers.toMutableList()
        val playerList = table.rows.map { row ->
            PlayerStats(
                id = row["id"] as Int,
                nickname = row["nickname"] as String,
                name = row["first_name"] as String,
                lastnames = row["last_name"] as String,
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
        // Add a check to prevent rendering the DataTable composable until headers and players have data
        if (headers.isNotEmpty() && players.isNotEmpty()) {
            DataTable(headers = headers, rows = players, modifier = Modifier.weight(1f))
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
    val currentSorting = remember { mutableStateOf("nickname") }

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
                .padding(8.dp)
                .clickable{
                    sortTable(headers.first(),rows, currentSorting = currentSorting)
                },
                contentAlignment = alignments[0],
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
                        .padding(8.dp)
                        .clickable{
                            sortTable(headers[index + 1], rows, currentSorting = currentSorting)
                        },
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
fun sortTable(header: String, rows: MutableList<PlayerStats>, currentSorting: MutableState<String>){
    var sorted = rows.sortedBy { it.name }
    when (header) {
        "id" -> {
            if (currentSorting.value == "id") {
                currentSorting.value = "idDescending"
                sorted = rows.sortedByDescending { it.id }
            } else {
                currentSorting.value = "id"
                sorted = rows.sortedBy { it.id }
            }
        }
        "nickname" -> {
            if (currentSorting.value == "nickname") {
                currentSorting.value = "nicknameDescending"
                sorted = rows.sortedByDescending { it.nickname }
            } else {
                currentSorting.value = "nickname"
                sorted = rows.sortedBy { it.nickname }
            }
        }
        "name" -> {
            if (currentSorting.value == "name") {
                currentSorting.value = "nameDescending"
                sorted = rows.sortedByDescending { it.name }
            } else {
                currentSorting.value = "name"
                sorted = rows.sortedBy { it.name }
            }
        }
        "lastnames" -> {
            if (currentSorting.value == "lastnames") {
                currentSorting.value = "lastnamesDescending"
                sorted = rows.sortedByDescending { it.lastnames }
            } else {
                currentSorting.value = "lastnames"
                sorted = rows.sortedBy { it.lastnames }
            }
        }
        "goals" -> {
            if (currentSorting.value == "goals") {
                currentSorting.value = "goalsDescending"
                sorted = rows.sortedByDescending { it.goals }
            } else {
                currentSorting.value = "goals"
                sorted = rows.sortedBy { it.goals }
            }
        }
        "own_goals" -> {
            if (currentSorting.value == "own_goals") {
                currentSorting.value = "own_goalsDescending"
                sorted = rows.sortedByDescending { it.own_goals }
            } else {
                currentSorting.value = "own_goals"
                sorted = rows.sortedBy { it.own_goals }
            }
        }
        "matches" -> {
            if (currentSorting.value == "matches") {
                currentSorting.value = "matchesDescending"
                sorted = rows.sortedByDescending { it.matches }
            } else {
                currentSorting.value = "matches"
                sorted = rows.sortedBy { it.matches }
            }
        }
        "puskas" -> {
            if (currentSorting.value == "puskas") {
                currentSorting.value = "puskasDescending"
                sorted = rows.sortedByDescending { it.puskas }
            } else {
                currentSorting.value = "puskas"
                sorted = rows.sortedBy { it.puskas }
            }
        }
    }
    rows.clear()
    rows.addAll(sorted)
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
