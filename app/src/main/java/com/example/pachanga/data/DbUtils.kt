package com.example.pachanga.data

import android.content.Context
import android.util.Log
import com.example.pachanga.shared.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Downloads and saves a new version of the database file.
 * @param context The application context.
 * @return True if the download and save were successful, false otherwise.
 */
suspend fun downloadDatabase(context: Context): Boolean {
    val dbPath = context.filesDir.path + "/databases/"
    val dbDir = File(dbPath)

    if (!dbDir.exists()) {
        dbDir.mkdirs()
    }

    return withContext(Dispatchers.IO) {
        try {
            val dbFile = File(dbPath, Constants.Database.NAME)
            val url = URL(Constants.Database.DOWNLOAD_FILE_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return@withContext false
            }

            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(dbFile)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            true
        } catch (e: Exception) {
            Log.e("DatabaseUtils", "Failed to download new database: ${e.message}", e)
            e.printStackTrace()
            false
        }
    }
}
