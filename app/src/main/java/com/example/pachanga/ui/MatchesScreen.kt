package com.example.pachanga.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pachanga.R
import com.example.pachanga.data.PachangaDbHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun Player(player: Map<String, Any?>, modifier: Modifier = Modifier){
    val starIcon = "\uD83C\uDF1F"
    val ownGoalsIcon = "\uD83D\uDD34"
    val goalsIcon = "⚽"

    val nickname = player["nickname"] as String
    val puskas = player["puskas"] == 1
    val goals = player["goals"] as Int
    val ownGoals = player["own_goals"] as Int
    val goalsText =  buildString {
        append(goalsIcon)
        append("x$goals")
        if (ownGoals > 0) {
            append(" ")
            append(ownGoalsIcon)
            append("x$ownGoals")
        }
    }
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box{
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {}
            ){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(player["profile_image_url"] as String)
                        .placeholder(R.drawable.ic_players)
                        .error(R.drawable.ic_players)
                        .crossfade(true)
                        .build(),
                    contentDescription = "$nickname image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = nickname,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = goalsText,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if (puskas){
                Text(starIcon,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}
@Composable
fun Team1 (
    players: List<Map<String, Any?>>,
    modifier: Modifier = Modifier
) {
    var i = 0
    val length = players.size
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
            if (length >= 6){
                Player(
                    player = players[i++],
                    Modifier.weight(1f)
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
            if (length >= 5){
                Player(
                    player = players[i++],
                    Modifier.weight(1f)
                )
            }
            if (length >= 7){
                Player(
                    player = players[i++],
                    Modifier.weight(1f)
                )
            }
        }
    }
}
@Composable
fun Team2 (
    players: List<Map<String, Any?>>,
    modifier: Modifier = Modifier
) {
    var i = 0
    val length = players.size
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
            if (length >= 5){
                Player(
                    player = players[i++],
                    Modifier.weight(1f)
                )
            }
            if (length >= 7){
                Player(
                    player = players[i++],
                    Modifier.weight(1f)
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
            if (length >= 6){
                Player(
                    player = players[i++],
                    Modifier.weight(1f)
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Player(
                player = players[i++],
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun MatchesScreen() {
    val context = LocalContext.current
    val team1 = remember { mutableStateListOf<Map<String, Any?>>() }
    val team2 = remember { mutableStateListOf<Map<String, Any?>>() }
    var matches by remember { mutableStateOf<List<Map<String, Any?>>>(emptyList()) }
    var selectedMatchIndex by remember { mutableIntStateOf(-1) }

    LaunchedEffect(Unit) {
        matches = PachangaDbHelper(context).queryTable(tableName = "vw_match").rows
        selectedMatchIndex = matches.size - 1
    }

    LaunchedEffect(selectedMatchIndex) {
        if (selectedMatchIndex == -1) return@LaunchedEffect
        val idMatch = matches[selectedMatchIndex]["id"]
        val table = PachangaDbHelper(context).queryTable(tableName = "vw_match_stats", whereClause = "id_match = $idMatch")
        team1.clear()
        team2.clear()

        team1.addAll(table.rows.filter {x -> x["team"] == 1 })
        team2.addAll(table.rows.filter { it["team"] == 2 })
        /* id_player == 6 is raul who is always the goalkeeper so we put him at
        the start or end of the array to later print him in the correct position */
        team1.firstOrNull { it["id_player"] == 6 }?.let {
            team1.remove(it)
            team1.add(0, it)
        }
        team2.firstOrNull { it["id_player"] == 6 }?.let {
            team2.remove(it)
            team2.add(it)
        }
    }




    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        if (team1.isNotEmpty() && team2.isNotEmpty()){
            val team1goals = team1.sumOf { it["goals"] as Int } + team2.sumOf { it["own_goals"] as Int }
            val team2goals = team2.sumOf { it["goals"] as Int } + team1.sumOf { it["own_goals"] as Int }
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(R.drawable.football_field_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .border(
                            BorderStroke(4.dp, Color.White),
                        )
                )
                Column(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Team1(
                        players = team1,
                        modifier = Modifier.weight(1f)
                    )

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(team1goals.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(team2goals.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    Team2(
                        players = team2,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading Match...")
            }
        }
        if (matches.isNotEmpty()){
            MatchSelectorButton(
                selectedIndex = selectedMatchIndex,
                onSelectedIndexChange = { newIndex -> selectedMatchIndex = newIndex },
                matches = matches,
                modifier = Modifier.fillMaxWidth().heightIn(max = 40.dp).padding(horizontal = 4.dp)
            )
        }
    }
}
@Composable
fun MatchSelectorButton(
    matches: List<Map<String,Any?>>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatIn = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateFormatOut = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.forLanguageTag("es-ES"))
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
                matches.getOrNull(prevIndex)?.let {
                    Text(LocalDateTime.parse( matches[prevIndex]["datetime"] as String, dateFormatIn).format(dateFormatOut),
                        maxLines = 1,
                        overflow = TextOverflow.Visible,
                        softWrap = false
                        )
                }
            },
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            onClick = { },
            icon = {  },
            selected = true,
            label = {
                Text(LocalDateTime.parse( matches[selectedIndex]["datetime"] as String, dateFormatIn).format(dateFormatOut))
            }
        )
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3
            ),
            onClick = { onSelectedIndexChange(nextIndex) },
            selected = false,
            enabled = nextIndex < matches.size,
            label = {
                matches.getOrNull(nextIndex)?.let {
                    Text(LocalDateTime.parse( matches[nextIndex]["datetime"] as String, dateFormatIn).format(dateFormatOut))
                }
            }
        )
    }
}