package com.rpl.masukerja.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "db_masukerja"
        private const val DATABASE_VERSION = 1

        private val SQl_CREATE_TABLE_TEST = "CREATE TABLE ${DatabaseContract.TestColumns.TABLE_NAME} (" +
                "${DatabaseContract.TestColumns.ID_QUESTION} INTEGER PRIMARY KEY," +
                "${DatabaseContract.TestColumns.CODE_ANSWER} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQl_CREATE_TABLE_TEST)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.TestColumns.TABLE_NAME}")
        onCreate(db)
    }
}