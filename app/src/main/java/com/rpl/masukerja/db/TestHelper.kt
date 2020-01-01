package com.rpl.masukerja.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TestHelper (context: Context) {

    private val databaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.TestColumns.TABLE_NAME
        private var INSTANCE: TestHelper? = null

        fun getInstance(context: Context): TestHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TestHelper(context)
                    }
                }
            }
            return INSTANCE as TestHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.TestColumns.ID_QUESTION}",
            null
        )
    }

    @Throws(SQLException::class)
    fun insert(values: ContentValues): Long {
        return  database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues): Int {
        return database.update(DATABASE_TABLE, values, "${DatabaseContract.TestColumns.ID_QUESTION} = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${DatabaseContract.TestColumns.ID_QUESTION} = $id", null)
    }

    fun deleteAll(): Int {
        return database.delete(DATABASE_TABLE, null, null)
    }
}