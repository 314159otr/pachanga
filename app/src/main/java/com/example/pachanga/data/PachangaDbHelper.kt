package com.example.pachanga.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File

class PachangaDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {

    private val dbPath = context.filesDir.path + "/databases/"
    private val dbFile = File(dbPath, DB_NAME)

    override fun onCreate(db: SQLiteDatabase?) {
        // Not needed if we ship a prebuilt DB in assets
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle migrations if schema changes
    }

    // Custom getter to check for the downloaded database file
    override fun getReadableDatabase(): SQLiteDatabase {
        if (dbFile.exists()) {
            Log.d("PachangaDbHelper", "Opening downloaded database at: ${dbFile.absolutePath}")
            return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READONLY)
        }
        Log.d("PachangaDbHelper", "Opening database from assets.")
        return super.getReadableDatabase()
    }

    // Custom getter to check for the downloaded database file
    override fun getWritableDatabase(): SQLiteDatabase {
        if (dbFile.exists()) {
            Log.d("PachangaDbHelper", "Opening downloaded database at: ${dbFile.absolutePath}")
            return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
        }
        Log.d("PachangaDbHelper", "Opening database from assets.")
        return super.getWritableDatabase()
    }


    companion object {
        private const val DB_NAME = "pachanga.db"
        private const val DB_VERSION = 1
    }
    data class DbTable(
        var headers: List<String> = emptyList(),
        val rows: MutableList<Map<String, Any?>> = mutableListOf()
    )
    fun  queryTable(
        tableName: String,
        whereClause: String? = null,
        whereArgs: Array<String>? = null,
    ): DbTable{
        val result = DbTable()
        val sql = if (whereClause.isNullOrBlank()){
            "select * from $tableName"
        } else {
            "select * from $tableName where $whereClause"
        }
        val db = readableDatabase
        val cursor = db.rawQuery(sql,whereArgs)
        cursor.use {
            val columnCount = cursor.columnCount
            val columnNames = cursor.columnNames
            result.headers = cursor.columnNames.toList()
            while (cursor.moveToNext()){
                val row = mutableMapOf<String, Any?>()
                for (i in 0 until columnCount) {
                    row[columnNames[i]] = when (cursor.getType(i)) {
                        Cursor.FIELD_TYPE_INTEGER -> cursor.getInt(i)
                        Cursor.FIELD_TYPE_FLOAT -> cursor.getFloat(i)
                        Cursor.FIELD_TYPE_STRING -> cursor.getString(i)
                        Cursor.FIELD_TYPE_BLOB -> cursor.getBlob(i)
                        Cursor.FIELD_TYPE_NULL -> null
                        else -> null
                    }
                }
                result.rows.add(row)
            }
        }
        return result
    }
}
