package com.bignerdranch.android.fitnesspal.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.util.*

class TrainingSessionType {
    var id: Long? = null
        set(id) {
            field = this.id
        }
    private val name: String

    constructor(name: String) {
        this.name = name
    }

    companion object {

        fun getContentValues(TrainingSessionType: TrainingSessionType): ContentValues {
            val values = ContentValues()
            values.put("name", TrainingSessionType.name)
            return values
        }

        fun getTrainingSessionTypes(database: SQLiteDatabase): List<TrainingSessionType> {
            val trainingSessionTypes = LinkedList<TrainingSessionType>()

            val cursor = database.rawQuery("select * from training_session_types", null)

            try {
                cursor.moveToFirst()

                while (!cursor.isAfterLast) {
                    trainingSessionTypes.add(TrainingSessionType(cursor.getLong(0), cursor.getString(1)))
                    cursor.moveToNext()
                }
            } finally {
                cursor.close()
            }

            return trainingSessionTypes
        }

        fun getTrainingSessionTypeByName(database: SQLiteDatabase, name: String): TrainingSessionType? {
            var trainingSessionType: TrainingSessionType? = null
            val query = "select * from training_session_types where name = '$name';"
            val cursor = database.rawQuery(query, null)

            try {
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    trainingSessionType = TrainingSessionType(cursor.getLong(0), cursor.getString(1))
                }
            } finally {
                cursor.close()
            }

            return trainingSessionType
        }
    }
}
