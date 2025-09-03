package com.example.pachanga.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.R
import com.example.pachanga.data.downloadDatabase
import com.example.pachanga.shared.Constants
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun GreetingScreen(navController: NavController) {
    val context = LocalContext.current
    var downloadStatus by remember { mutableStateOf<String?>(null) }
    var isDownloading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var downloadNeeded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.welcome_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (isDownloading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = downloadStatus ?: "Descargando...",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else if (downloadStatus != null) {
                Text(
                    text = downloadStatus!!,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (!isDownloading) {
                        isDownloading = true
                        downloadStatus = "Descargando..."

                        // Launch a coroutine to handle the download in the background
                        coroutineScope.launch {
                            val success = downloadDatabase(context)

                            if (success) {
                                downloadStatus = "Descarga Completada!"
                                downloadNeeded = false
                            } else {
                                downloadStatus = "Descarga Fallida!"
                            }
                            isDownloading = false
                        }
                    }
                },
                enabled = !isDownloading
            ) {
                Text("Descargar última versión")
            }
            if (downloadNeeded) {
                Text(
                    text = " ↑ Necesita descargar para acceder ↑ ",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))

                )
            }
            Button(
                onClick = {
                    val dbFilePath = File(context.filesDir.path + "/databases/pachanga.db")
                    if (dbFilePath.exists()){
                        navController.navigate(Constants.Nav.PLAYERS_STATS.route)
                    }else{
                        downloadNeeded = true
                    }

                },
                enabled = !downloadNeeded
            ) {
                Text(
                    text = "Pachanga!",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGreetingScreen() {
    GreetingScreen(navController = rememberNavController())
}
