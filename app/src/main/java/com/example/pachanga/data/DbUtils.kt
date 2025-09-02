// com/example/pachanga/data/DbUtils.kt
package com.example.pachanga.data

import android.content.Context
import java.io.FileOutputStream

fun copyDatabaseFromAssets(context: Context, dbName: String) {
    val dbPath = context.getDatabasePath(dbName)
    if (!dbPath.exists()) {
        dbPath.parentFile?.mkdirs()
        context.assets.open(dbName).use { input ->
            FileOutputStream(dbPath).use { output ->
                input.copyTo(output)
            }
        }
    }
}
