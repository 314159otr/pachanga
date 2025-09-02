package com.example.pachanga.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


fun copyDatabaseFromAssets(context: Context, fileName: String) {
    // The path to the databases folder
    val dbPath = context.filesDir.path + "/databases/"
    val dbFile = File(dbPath, fileName)

    // Only copy if the file does not exist.
    if (!dbFile.exists()) {
        Log.d("DatabaseUtils", "Database file not found, copying from assets...")
        try {
            // Ensure the databases directory exists
            File(dbPath).mkdirs()

            // Open the input stream from assets
            val inputStream: InputStream = context.assets.open(fileName)

            // Open the output stream to the internal storage
            val outputStream = FileOutputStream(dbFile)

            // Use the Kotlin `copyTo` extension function, which is efficient and safe
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            Log.d("DatabaseUtils", "Database copied successfully.")

        } catch (e: IOException) {
            // Log the exact error to help with debugging
            Log.e("DatabaseUtils", "Failed to copy database file: ${e.message}", e)
        }
    } else {
        Log.d("DatabaseUtils", "Database file already exists, skipping copy.")
    }
}

/**
 * Downloads and saves a new version of the database file.
 * @param context The application context.
 * @param urlString The URL of the remote database file.
 * @param fileName The name of the database file (e.g., "pachanga.db").
 * @return True if the download and save were successful, false otherwise.
 */
suspend fun downloadDatabase(context: Context, urlString: String, fileName: String): Boolean {
    // The path to the databases folder
    val dbPath = context.filesDir.path + "/databases/"
    val dbDir = File(dbPath)

    // Ensure the databases directory exists
    if (!dbDir.exists()) {
        dbDir.mkdirs() // This will create the directory and any necessary parent directories
    }

    return withContext(Dispatchers.IO) {
        try {
            val dbFile = File(dbPath, fileName)
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                Log.e("DatabaseUtils", "Download failed with HTTP error code: ${connection.responseCode}")
                return@withContext false
            }

            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(dbFile)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            // Explicitly close streams
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            Log.d("DatabaseUtils", "New database downloaded and saved successfully.")
            true
        } catch (e: Exception) {
            Log.e("DatabaseUtils", "Failed to download new database: ${e.message}", e)
            e.printStackTrace()
            false
        }
    }
}
