// com/example/pachanga/data/DbUtils.kt
package com.example.pachanga.data

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * Copies the database from the assets folder to the internal storage.
 * It only performs the copy if the file does not already exist in internal storage.
 * @param context The application context.
 * @param fileName The name of the database file.
 */
fun copyDatabaseFromAssets(context: Context, fileName: String) {
    val dbFile = File(context.filesDir, fileName)

    // Only copy if the file does not exist
    if (!dbFile.exists()) {
        try {
            val inputStream = context.assets.open(fileName)
            val outputStream = FileOutputStream(dbFile)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error copying initial database.", Toast.LENGTH_SHORT).show()
        }
    }
}
suspend fun downloadDatabase(context: Context, urlString: String, fileName: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                // Handle server-side errors
                return@withContext false
            }

            val inputStream = connection.inputStream
            val outputStream = FileOutputStream(File(context.filesDir, fileName))
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
            e.printStackTrace()
            // Handle network or file I/O exceptions
            false
        }
    }
}
