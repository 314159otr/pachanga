package com.example.pachanga.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pachanga.R
import com.example.pachanga.data.PachangaDbHelper


@Composable
fun Player(player: Map<String, Any?>, modifier: Modifier = Modifier){
    val star = "\uD83C\uDF1F"
    val goals = player["goals"]
    val nickname = player["nickname"] as String
    val puskas = player["puskas"] == 1
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
                )
                Text(
                    text = nickname,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "⚽x$goals",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if (puskas){
                Text(star,
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

    LaunchedEffect(Unit) {
        val table = PachangaDbHelper(context).queryTable("vw_match_stats","id_match = ?",arrayOf("35"))
        team1.clear()
        team2.clear()
        team1.addAll(table.rows.filter { x -> x["team"] == 1 })
        team2.addAll(table.rows.filter { x -> x["team"] == 2 })
    }

    if (team1.isNotEmpty() && team2.isNotEmpty()){
        Box(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxSize()
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
                    Text("15")
                    Text("14")
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

}