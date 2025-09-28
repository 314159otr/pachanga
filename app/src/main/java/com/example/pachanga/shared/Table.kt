package com.example.pachanga.shared

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun DataTable(
    headers: List<String>,
    rows: MutableList<Map<String, Any?>>,
    modifier: Modifier
) {
    val context = LocalContext.current
    val headerHeight = 48.dp
    val rowHeight = 48.dp
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState  = rememberScrollState()
    val widths = IntArray(headers.size)
    val currentSorting = remember { mutableStateOf("nickname") }
    val colors = arrayOf(Color.White, Color.LightGray)

    val alignments = Array(headers.size) { index ->
        if (rows[0][headers[index]] is Number) {
            Alignment.CenterEnd
        } else {
            Alignment.CenterStart
        }
    }
    headers.forEachIndexed { index, header ->
        widths[index] = header.getStringWidthInDp(context)
    }
    rows.forEach { row ->
        for (i in 0 until headers.size) {
            val length = row[headers[i]].toString().getStringWidthInDp(context)
            if (length > widths[i]) {
                widths[i] = length
            }
        }
    }

    Column (modifier = modifier){
        Row (modifier = Modifier.background(Color.Gray)){
            // first header
            Box(modifier = Modifier
                .width(widths[0].dp + 16.dp)
                .height(headerHeight)
                .background(Color.Gray)
                .padding(8.dp)
                .clickable{
                    sortTable(headers.first(), rows, currentSorting = currentSorting)
                },
                contentAlignment = alignments[0],
            ){
                Text(text = headers.first(), fontWeight = FontWeight.Bold)
            }
            // headers
            Row(modifier = Modifier
                .horizontalScroll(horizontalScrollState, overscrollEffect = null)
            ){
                headers.drop(1).forEachIndexed { index, header ->
                    Box(modifier = Modifier
                        .width(widths[index + 1].dp + 16.dp)
                        .height(headerHeight)
                        .background(Color.Gray)
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
                .verticalScroll(verticalScrollState, overscrollEffect = null)
            ) {
                rows.forEachIndexed { i, row ->
                    Box(
                        modifier = Modifier
                            .width(widths[0].dp + 16.dp)
                            .height(rowHeight)
                            .background(colors[i % 2])
                            .padding(8.dp),
                        contentAlignment = alignments[0]
                    ) {
                        Text(text = row[headers[0]].toString(), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
            // columns
            Box(modifier = Modifier
                .weight(1f)
                .horizontalScroll(horizontalScrollState, overscrollEffect = null)
                .verticalScroll(verticalScrollState, overscrollEffect = null)
            ){
                Column {
                    rows.forEachIndexed { index, row ->
                        Row(modifier = Modifier
                            .background(colors[index % 2])) {
                            for (i in 1 until headers.size){
                                Box(modifier = Modifier
                                    .width(widths[i].dp + 16.dp)
                                    .height(rowHeight)
                                    .padding(8.dp),
                                    contentAlignment = alignments[i]
                                ) {
                                    Text(text = row[headers[i]].toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
fun sortTable(header: String, rows: MutableList<Map<String, Any?>>, currentSorting: MutableState<String>){
    if(rows.isEmpty()) return
    val sample = rows[0][header]
    val sorted = when (sample) {
        is Number ->
            if (currentSorting.value == header) {
                rows.sortedByDescending { (it[header] as Number).toDouble() }
            } else {
                rows.sortedBy { (it[header] as Number).toDouble() }
            }
        is String ->
            if (currentSorting.value == header) {
                rows.sortedByDescending { (it[header] as String)}
            } else {
                rows.sortedBy { (it[header] as String)}
            }
        else -> rows.toList()
    }
    rows.clear()
    rows.addAll(sorted)
    if (currentSorting.value == header) {
        currentSorting.value = header + "Descending"
    } else {
        currentSorting.value = header
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
