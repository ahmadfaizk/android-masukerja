package com.rpl.masukerja.utils

import android.database.Cursor
import com.rpl.masukerja.db.DatabaseContract
import com.rpl.masukerja.model.Test

object MappingHelper {
    fun mapCursorToList(cursor: Cursor): ArrayList<Test> {
        val list = ArrayList<Test>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val idQuestion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TestColumns.ID_QUESTION))
            val codeAnswer = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TestColumns.CODE_ANSWER))
            list.add(Test(idQuestion, codeAnswer))
            cursor.moveToNext()
        }
        return list
    }
}