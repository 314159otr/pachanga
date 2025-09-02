package com.example.pachanga.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pachanga.R
import com.example.pachanga.data.copyDatabaseFromAssets
import com.example.pachanga.data.downloadDatabase
import kotlinx.coroutines.launch

@Composable
fun GreetingScreen(navController: NavController) {
    val context = LocalContext.current
    var downloadStatus by remember { mutableStateOf<String?>(null) }
    var isDownloading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Use LaunchedEffect to handle the initial database copy from assets
    LaunchedEffect(key1 = Unit) {
        copyDatabaseFromAssets(context, "pachanga.db")
    }

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
            Text(
                text = "Pachanga!",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (isDownloading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = downloadStatus ?: "Downloading...",
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
                        downloadStatus = "Checking for updates..."

                        // Launch a coroutine to handle the download in the background
                        coroutineScope.launch {
                            val downloadUrl = "https://github.com/314159otr/pachanga/blob/main/app/src/main/assets/pachanga.db" // Replace with your actual URL
                            val success = downloadDatabase(context, downloadUrl, "pachanga.db")

                            if (success) {
                                downloadStatus = "Download successful!"
                                // Navigate to the next screen after a successful download
                                navController.navigate("stats")
                            } else {
                                downloadStatus = "Download failed."
                            }
                            isDownloading = false
                        }
                    }
                },
                enabled = !isDownloading
            ) {
                Text("Start")
            }
        }
    }
}

@Preview
@Composable
fun PreviewGreetingScreen() {
    GreetingScreen(navController = rememberNavController())
}
