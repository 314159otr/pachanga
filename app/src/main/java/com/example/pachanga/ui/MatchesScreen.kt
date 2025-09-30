package com.example.pachanga.ui

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MatchesScreen() {

    val topPlayers = listOf<String>("Piotr ⚽⚽⚽", "unai ⚽⚽⚽⚽", "javier ⚽⚽⚽⚽⚽", "pedro ⚽⚽")
    val bottomPlayers = listOf<String>("Piotr ⚽", "unai ⚽⚽⚽", "javier ⚽⚽⚽", "pedro ⚽","aaa ⚽⚽⚽⚽⚽⚽⚽⚽","xxxxxxxxxxxx ⚽⚽⚽⚽⚽⚽⚽⚽⚽⚽","septtimo")
    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)       // take top half
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            topPlayers.forEach { player ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp)
                        .heightIn(min = 48.dp),
                ) {
                    Text(player)
                }
                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )

            }
        }

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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalDivider(
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            bottomPlayers.forEach { player ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(8.dp)
                        .height(48.dp),
                ) {
                    Text(player)
                }
                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
            }
        }
    }

}