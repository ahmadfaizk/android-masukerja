package com.rpl.masukerja.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class TestColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "test"
            const val ID_QUESTION = "id_question"
            const val CODE_ANSWER = "code_answer"
        }
    }
}