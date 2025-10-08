package com.example.pachanga.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun Player(player: String, modifier: Modifier = Modifier,puskas: Boolean = false) {
    val star = "\uD83C\uDF1F"
    val goals = player.length
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box{
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {},
            ){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://res.cloudinary.com/dlf47buhm/image/upload/v1748251890/Pito_qtg3f3.png")
                        .placeholder(R.drawable.ic_players)
                        .error(R.drawable.ic_players)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Player 1",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )
                Text(
                    text = player,
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
fun Team4 (
    players: List<String>,
    modifier: Modifier = Modifier
) {
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
                player = players[0],
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
                player = players[1],
                Modifier.weight(1f)
            )
            Player(
                player = players[2],
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
                player = players[3],
                Modifier.weight(1f),
                true
            )
        }
    }
}

@Composable
fun MatchesScreen() {

    val topPlayers = listOf<String>("Piotr", "unai", "javier", "pedro")
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
        )
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxSize()
        ) {
            Team4(
                players = topPlayers,
                modifier = Modifier.weight(1f)
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("5")
                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
                Text("3")
            }

            Team4(
                players = topPlayers,
                modifier = Modifier.weight(1f)
            )
        }
    }

}